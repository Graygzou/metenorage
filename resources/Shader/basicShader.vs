#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 textureCoordinates;
layout (location = 2) in vec3 vertexNormal;

out vec2 outTextureCoordinate;
out vec3 modelViewVertexNormal;
out vec3 modelViewVertexPosition;

uniform mat4 modelViewMatrix;
uniform mat4 projectionMatrix;

void main()
{
    vec4 modelViewPosition = modelViewMatrix * vec4(position, 1.0);
    gl_Position = projectionMatrix * modelViewPosition;
    outTextureCoordinate = textureCoordinates;

    // Set normal's weight to 0 to avoid translating it (we are only interested in its direction.)
    modelViewVertexNormal = normalize(modelViewMatrix * vec4(vertexNormal, 0.0)).xyz;
    modelViewVertexPosition = modelViewPosition.xyz;
}