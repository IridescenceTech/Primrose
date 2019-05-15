#version 330

layout(location = 0) in vec3 inPosition;
layout(location = 1) in vec2 inTexcoord;
layout(location = 2) in vec3 inNormal;

out vec2 texcoord;
out vec3 normal;
out vec3 fragPos;


uniform mat4 projViewMatrix;
uniform mat4 modelMatrix;

void main()
{
    fragPos = vec3(modelMatrix * vec4(inPosition, 1.0));
    normal = mat3(transpose(inverse(modelMatrix))) * inNormal;
    gl_Position = projViewMatrix * vec4(fragPos, 1.0);

    texcoord = inTexcoord;
}