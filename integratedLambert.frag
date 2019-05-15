#version 330

struct AmbientLight{
    vec3 color;
    float intensity;
};

struct DirectionalLight{
    vec3 direction;
    vec3 color;
    float intensity;
};

struct PointLight{
    vec3 position;
    vec3 color;
    float intensity;
    float linear;
    float quadratic;
};

struct SpotLight{
    vec3 position;
    vec3 direction;
    vec3 color;
    float intensity;
    float cutOff;
    float linear;
    float quadratic;
};

struct Material{
    sampler2D map;
};

uniform AmbientLight ambient;
uniform Material material;
uniform vec3 cameraPosition;

#define NUMBER_OF_DIRECTIONAL_MAX 4
#define NUMBER_OF_POINT_LIGHT_MAX 16
#define NUMBER_OF_SPOT_LIGHTS_MAX 8

uniform DirectionalLight directional[NUMBER_OF_DIRECTIONAL_MAX];
uniform PointLight pointLights[NUMBER_OF_POINT_LIGHT_MAX];
uniform SpotLight spotLights[NUMBER_OF_SPOT_LIGHTS_MAX];

out vec4 outColor;
in vec2 texcoord;
in vec3 normal;
in vec3 fragPos;

bool toon;

vec3 CalcDirectional(DirectionalLight light);
vec3 CalcPoint(PointLight light);
vec3 CalcSpot(SpotLight light);

vec4 diffuseMap = texture(material.map, texcoord);
void main(){
    vec3 result = vec3(0);

    //Why calculate lighting on something that's transparent?
    if(diffuseMap.a < 0.1){
        discard;
    }

    //Ambient
    vec3 ambientFactor = ambient.color * ambient.intensity * diffuseMap.rgb;
    result = ambientFactor;

    //LIGHTING PASS:

    for(int i = 0; i < NUMBER_OF_DIRECTIONAL_MAX; i++)
    result += CalcDirectional(directional[i]);

    for(int i = 0; i < NUMBER_OF_POINT_LIGHT_MAX; i++)
    result += CalcPoint(pointLights[i]);

    for(int i = 0; i < NUMBER_OF_SPOT_LIGHTS_MAX; i++)
    result += CalcSpot(spotLights[i]);

    if(toon){
        outColor = vec4(floor(result.rgb * 8.0f) / 8.0f, 1.0f);
    }else{
        outColor = vec4(result, diffuseMap.a);
    }
}

vec3 CalcDirectional(DirectionalLight dir){
    if(dir.intensity > 0){
        vec3 norm = normalize(normal);
        vec3 lightDir = normalize(-dir.direction);
        vec3 viewDir = normalize(cameraPosition - fragPos);

        float diff = max(dot(norm, lightDir), 0.0);
        vec3 diffuse = dir.intensity * dir.color * diff * diffuseMap.rgb;
        return (diffuse);
    }else{
        return vec3(0.0f);
    }
}

vec3 CalcPoint(PointLight point){
    if(point.intensity > 0){
        vec3 norm = normalize(normal);
        vec3 viewDir = normalize(cameraPosition - fragPos);
        vec3 lightDir = normalize(point.position - fragPos);

        float diff = max(dot(norm, lightDir), 0.0);
        vec3 diffuse = point.intensity * point.color * diff * diffuseMap.rgb;

        float distance = length(point.position - fragPos);
        float attenuation = 1.0 / (1 + point.linear * distance + point.quadratic * (distance * distance));
        diffuse *= attenuation;
        return (diffuse);
    }else{
        return vec3(0.0f);
    }
}

vec3 CalcSpot(SpotLight spot){
    if(spot.intensity > 0 && spot.cutOff > 0){
        vec3 lightDir = normalize(spot.position - fragPos);
        vec3 norm = normalize(normal);
        float diff = max(dot(norm, lightDir), 0.0);
        vec3 viewDir = normalize(cameraPosition - fragPos);

        // attenuation
        float distance = length(spot.position - fragPos);
        float attenuation = 1.0 / (1.0f + spot.linear * distance + spot.quadratic * (distance * distance));
        // spotlight intensity
        float theta = dot(lightDir, normalize(-spot.direction));
        float epsilon = spot.cutOff;
        float intensity = clamp((theta) / epsilon, 0.0, 1.0);
        // combine results
        vec3 diffuse = spot.color * spot.intensity * diff * diffuseMap.rgb;

        diffuse *= attenuation * intensity;
        return (diffuse);
    }else {
        return vec3(0.0f);
    }
}