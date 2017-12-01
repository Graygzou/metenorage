#version 330

struct Attenuation
{
    float constant;
    float linear;
    float exponent;
};

struct PointLight
{
    vec3 color;
    vec3 position; // In view coordinates.
    float intensity;
    Attenuation attenuation;
};

struct Material
{
    // Colors used if no texture given.
    vec4 ambient;
    vec4 diffuse;
    vec4 specular;
    int hasTexture;
    float reflectance;
};

in vec2 outTextureCoordinate;
in vec3 modelViewVertexPosition;
in vec3 modelViewVertexNormal;
out vec4 fragmentColor;

uniform sampler2D textureSampler;
uniform vec3 color;
uniform int useColor;

uniform vec3 ambientLight;
uniform float specularPower;
uniform Material material;
uniform PointLight pointLight;
uniform vec3 cameraPosition;

vec4 ambientConstant;
vec4 diffuseConstant;
vec4 specularConstant;

void setupColors(Material material, vec2 textureCoordinates)
{
    if (material.hasTexture == 1)
    {
        ambientConstant = texture(textureSampler, textureCoordinates);
        diffuseConstant = ambientConstant;
        specularConstant = ambientConstant;
    }
    else
    {
        ambientConstant = material.ambient;
        diffuseConstant = material.diffuse;
        specularConstant = material.specular;
    }
}

vec4 computePointLight(PointLight light, vec3 position, vec3 normal)
{
    vec4 diffuseColor = vec4(0, 0, 0, 0);
    vec4 specularColor = vec4(0, 0, 0, 0);

    // Diffuse Light
    vec3 lightDirection = light.position - position;
    vec3 toLightSource  = normalize(lightDirection);
    float diffuseFactor = max(dot(normal, toLightSource), 0.0);
    diffuseColor = diffuseConstant * vec4(light.color, 1.0) * light.intensity * diffuseFactor;

    // Specular Light
    vec3 cameraDirection = normalize(-position);
    vec3 fromLightSource = -toLightSource;
    vec3 reflectedLight = normalize(reflect(fromLightSource, normal));
    float specularFactor = max(dot(cameraDirection, reflectedLight), 0.0);
    specularFactor = pow(specularFactor, specularPower);
    specularColor = specularConstant * specularFactor * material.reflectance * vec4(light.color, 1.0);

    // Attenuation
    float distance = length(lightDirection);
    float attenuationInverse = light.attenuation.constant + light.attenuation.linear * distance +
        light.attenuation.exponent * distance * distance;
    return (diffuseColor + specularColor) / attenuationInverse;
}

void main()
{
    setupColors(material, outTextureCoordinate);

    vec4 diffuseSpecularComponent = computePointLight(pointLight, modelViewVertexPosition, modelViewVertexNormal);

    fragmentColor = ambientConstant * vec4(ambientLight, 1) + diffuseSpecularComponent;
}