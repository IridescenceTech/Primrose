package org.iridescence.primrose.graphics.shaders;

public class ToonShader extends Shader {
  public static final String vs =
      "#version 330\n"
          + "\n"
          + "layout(location = 0) in vec3 inPosition;\n"
          + "layout(location = 1) in vec2 inTexcoord;\n"
          + "layout(location = 2) in vec3 inNormal;\n"
          + "\n"
          + "out vec2 texcoord;\n"
          + "out vec3 normal;\n"
          + "out vec3 fragPos;\n"
          + "\n"
          + "\n"
          + "uniform mat4 projViewMatrix;\n"
          + "uniform mat4 modelMatrix;\n"
          + "\n"
          + "void main()\n"
          + "{\n"
          + "    fragPos = vec3(modelMatrix * vec4(inPosition, 1.0));\n"
          + "    normal = mat3(transpose(inverse(modelMatrix))) * inNormal;\n"
          + "    gl_Position = projViewMatrix * vec4(fragPos, 1.0);\n"
          + "\n"
          + "    texcoord = inTexcoord;\n"
          + "}";
  public static final String fs =
      "#version 330\n"
          + "\n"
          + "struct AmbientLight{\n"
          + "    vec3 color;\n"
          + "    float intensity;\n"
          + "};\n"
          + "\n"
          + "struct DirectionalLight{\n"
          + "    vec3 direction;\n"
          + "    vec3 color;\n"
          + "    float intensity;\n"
          + "};\n"
          + "\n"
          + "struct PointLight{\n"
          + "    vec3 position;\n"
          + "    vec3 color;\n"
          + "    float intensity;\n"
          + "    float linear;\n"
          + "    float quadratic;\n"
          + "};\n"
          + "\n"
          + "struct SpotLight{\n"
          + "    vec3 position;\n"
          + "    vec3 direction;\n"
          + "    vec3 color;\n"
          + "    float intensity;\n"
          + "    float cutOff;\n"
          + "    float linear;\n"
          + "    float quadratic;\n"
          + "};\n"
          + "\n"
          + "struct Material{\n"
          + "    sampler2D map;\n"
          + "};\n"
          + "\n"
          + "uniform AmbientLight ambient;\n"
          + "uniform Material material;\n"
          + "uniform vec3 cameraPosition;\n"
          + "uniform float colorShades;\n"
          + "\n"
          + "#define NUMBER_OF_DIRECTIONAL_MAX 4\n"
          + "#define NUMBER_OF_POINT_LIGHT_MAX 16\n"
          + "#define NUMBER_OF_SPOT_LIGHTS_MAX 8\n"
          + "\n"
          + "uniform DirectionalLight directional[NUMBER_OF_DIRECTIONAL_MAX];\n"
          + "uniform PointLight pointLights[NUMBER_OF_POINT_LIGHT_MAX];\n"
          + "uniform SpotLight spotLights[NUMBER_OF_SPOT_LIGHTS_MAX];\n"
          + "\n"
          + "out vec4 outColor;\n"
          + "in vec2 texcoord;\n"
          + "in vec3 normal;\n"
          + "in vec3 fragPos;\n"
          + "\n"
          + "vec3 CalcDirectional(DirectionalLight light);\n"
          + "vec3 CalcPoint(PointLight light);\n"
          + "vec3 CalcSpot(SpotLight light);\n"
          + "\n"
          + "vec4 diffuseMap = texture(material.map, texcoord);\n"
          + "\n"
          + "void main(){\n"
          + "    vec3 result = vec3(0);\n"
          + "\n"
          + "    //Why calculate lighting on something that's transparent?\n"
          + "    if(diffuseMap.a < 0.1){\n"
          + "        discard;\n"
          + "    }\n"
          + "\n"
          + "    //Ambient\n"
          + "    vec3 ambientFactor = ambient.color * ambient.intensity * diffuseMap.rgb;\n"
          + "    result = ambientFactor;\n"
          + "\n"
          + "    //LIGHTING PASS:\n"
          + "\n"
          + "    for(int i = 0; i < NUMBER_OF_DIRECTIONAL_MAX; i++)\n"
          + "        result += CalcDirectional(directional[i]);\n"
          + "\n"
          + "    for(int i = 0; i < NUMBER_OF_POINT_LIGHT_MAX; i++)\n"
          + "        result += CalcPoint(pointLights[i]);\n"
          + "\n"
          + "    for(int i = 0; i < NUMBER_OF_SPOT_LIGHTS_MAX; i++)\n"
          + "        result += CalcSpot(spotLights[i]);\n"
          + "\n"
          + "    outColor = vec4(floor(result * colorShades)/colorShades, diffuseMap.a);\n"
          + "}\n"
          + "\n"
          + "vec3 CalcDirectional(DirectionalLight dir){\n"
          + "    if(dir.intensity > 0){\n"
          + "        vec3 lightDir = normalize(-dir.direction);\n"
          + "\n"
          + "        float diff = max(dot(normalize(normal), lightDir), 0.0);\n"
          + "        vec3 diffuse = dir.intensity * dir.color * diff * diffuseMap.rgb;\n"
          + "\n"
          + "\n"
          + "        return (diffuse);\n"
          + "    }else{\n"
          + "        return vec3(0.0f);\n"
          + "    }\n"
          + "}\n"
          + "\n"
          + "vec3 CalcPoint(PointLight point){\n"
          + "    if(point.intensity > 0){\n"
          + "        vec3 lightDir = normalize(point.position - fragPos);\n"
          + "\n"
          + "        float diff = max(dot(normalize(normal), lightDir), 0.0);\n"
          + "\n"
          + "\n"
          + "        float distance = length(point.position - fragPos);\n"
          + "        float attenuation = 1.0 / (1 + point.linear * distance + point.quadratic * (distance * distance));\n"
          + "        vec3 diffuse = point.intensity * point.color * diff * diffuseMap.rgb;\n"
          + "\n"
          + "\n"
          + "        diffuse *= attenuation;\n"
          + "        return (diffuse);\n"
          + "    }else{\n"
          + "        return vec3(0.0f);\n"
          + "    }\n"
          + "}\n"
          + "\n"
          + "vec3 CalcSpot(SpotLight spot){\n"
          + "    if(spot.intensity > 0 && spot.cutOff > 0){\n"
          + "        vec3 lightDir = normalize(spot.position - fragPos);\n"
          + "        // diffuse shading\n"
          + "        float diff = max(dot(normalize(normal), lightDir), 0.0);\n"
          + "        // attenuation\n"
          + "        float distance = length(spot.position - fragPos);\n"
          + "        float attenuation = 1.0 / (1.0f + spot.linear * distance + spot.quadratic * (distance * distance));\n"
          + "        // spotlight intensity\n"
          + "        float theta = dot(lightDir, normalize(-spot.direction));\n"
          + "        float epsilon = spot.cutOff;\n"
          + "        float intensity = clamp((theta) / epsilon, 0.0, 1.0);\n"
          + "        // combine results\n"
          + "        vec3 diffuse = spot.color * spot.intensity * diff * diffuseMap.rgb;\n"
          + "\n"
          + "        diffuse *= attenuation * intensity;\n"
          + "        return (diffuse);\n"
          + "    }else {\n"
          + "        return vec3(0.0f);\n"
          + "    }\n"
          + "}";
  /** A static instance of the shader. */
  public static final ToonShader shader = new ToonShader();

  public ToonShader() {
    super(vs, fs, false);
  }
}
