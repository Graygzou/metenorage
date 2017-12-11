#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 textureCoordinates;
layout (location = 2) in vec3 vertexNormal;

out vec2 outTextureCoordinates;

uniform mat4 projectionModelMatrix;

void main()
{
    gl_Position = projectionModelMatrix * vec4(position, 1.0);
    outTextureCoordinates = textureCoordinates;
}