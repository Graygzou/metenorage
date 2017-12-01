#version 330

layout (location =0) in vec4 in_Position;
layout (location =1) in vec2 texCoord;

out vec2 outTexCoord;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

void main(void) {
    gl_Position = projectionMatrix * modelViewMatrix * in_Position;
    outTexCoord = texCoord;
}