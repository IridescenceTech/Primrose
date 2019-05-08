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
    float outerCutOff;

    float linear;
    float quadratic;
};

struct Material{
    sampler2D map;
    sampler2D specMap;
    float specularIntensity;
    float shininess;
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

vec3 CalcDirectional(DirectionalLight light);
vec3 CalcPoint(PointLight light);
vec3 CalcSpot(SpotLight light);

vec4 diffuseMap = texture(material.map, texcoord);
vec4 specMap = texture(material.specMap, texcoord);

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

    outColor = vec4(result, diffuseMap.a);
}

vec3 CalcDirectional(DirectionalLight dir){
    if(dir.intensity > 0){
        vec3 lightDir = normalize(-dir.direction);

        float diff = max(dot(normalize(normal), lightDir), 0.0);
        vec3 diffuse = dir.intensity * dir.color * diff * diffuseMap.rgb;

        vec3 specularFactor = vec3(0.0f);
        if(dot(normalize(normal), lightDir) >= 0.0f){
            vec3 viewDir = normalize(cameraPosition - fragPos);
            vec3 reflectDir = reflect(-lightDir, normalize(normal));
            float spec = pow(max(dot(viewDir, reflectDir), 0), pow(2, material.shininess * 10));
            specularFactor = material.specularIntensity * spec * dir.color * dir.intensity * specMap.rgb;
        }

        return (diffuse + specularFactor);
    }else{
        return vec3(0.0f);
    }
}

vec3 CalcPoint(PointLight point){
    if(point.intensity > 0){
        vec3 lightDir = normalize(point.position - fragPos);

        float diff = max(dot(normalize(normal), lightDir), 0.0);

        vec3 specularFactor = vec3(0.0f);
        if(dot(normalize(normal), lightDir) >= 0.0f){
            vec3 viewDir = normalize(cameraPosition - fragPos);
            vec3 reflectDir = reflect(-lightDir, normalize(normal));
            float spec = pow(max(dot(viewDir, reflectDir), 0), pow(2, material.shininess * 10));
            specularFactor = material.specularIntensity * spec * point.color * point.intensity * specMap.rgb;
        }

        float distance = length(point.position - fragPos);
        float attenuation = 1.0 / (1 + point.linear * distance + point.quadratic * (distance * distance));
        vec3 diffuse = point.intensity * point.color * diff * diffuseMap.rgb;


        diffuse *= attenuation;
        specularFactor *= attenuation;
        return (diffuse + specularFactor);
    }else{
        return vec3(0.0f);
    }
}

vec3 CalcSpot(SpotLight spot){
    if(spot.intensity > 0 && spot.cutOff > 0){
        vec3 lightDir = normalize(spot.position - fragPos);

        float diff = max(dot(normal, lightDir), 0.0);


        vec3 specularFactor = vec3(0.0f);
        if(dot(normalize(normal), lightDir) >= 0.0f){
            vec3 reflectDir = reflect(-lightDir, normalize(normal));
            float spec = pow(max(dot(normalize(cameraPosition - fragPos), reflectDir), 0), pow(2, material.shininess * 10));
            specularFactor = material.specularIntensity * spec * spot.color * spot.intensity * specMap.rgb;
        }

        float distance = length(spot.position - fragPos);
        float attenuation = 1.0 / (1 + spot.linear * distance + spot.quadratic * (distance * distance));

        float theta = dot(lightDir, normalize(-spot.direction));
        float epsilon = spot.cutOff - spot.outerCutOff;
        float intensity = clamp((theta - spot.outerCutOff) / epsilon, 0.0, 1.0);

        vec3 diffuse = spot.intensity * spot.color * diff * diffuseMap.rgb;

        diffuse *= attenuation * intensity;
        specularFactor *= attenuation * intensity;
        return (diffuse + specularFactor);
    }else {
        return vec3(0.0f);
    }
}