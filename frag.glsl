uniform float time;
uniform int pixelate;
uniform float k;
uniform vec3 metaballs[1000];
varying vec4 vertColor;

void main() {
  float x = floor(gl_FragCoord.x / pixelate) * pixelate;
  float y = floor(gl_FragCoord.y / pixelate) * pixelate;

  float s = 0;
  for (int i = 0; i < 1000; i++) {
    vec3 mb = metaballs[i];
    float dx = mb.x - x;
    float dy = mb.y - y;
    float r = mb.z;
    s += (r * r) / ((dx * dx) + (dy * dy));
  }

  if (s < k / 3) {
    gl_FragColor = vec4(0);
  } else if (s < (2 * k) / 3) {
    gl_FragColor = vec4(1, 0, 1, 1);
  } else if (s < k) {
    gl_FragColor = vec4(1, 1, 0, 1);
  } else {
    gl_FragColor = vec4(0, 1, 1, 1);
  }
}
