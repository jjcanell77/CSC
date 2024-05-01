const shell = require('gl-now')()
const createShader = require('gl-shader')
const createBuffer = require('gl-buffer')
const mat4 = require('gl-mat4')

const initialVertices = [
  0.0, 0.5, 0.0,
  -0.5, -0.5, 0.0,
  0.5, -0.5, 0.0 
];
const depth = 6;// can adjust the loop as needed

function divideTriangle(vertices, depth) {
  let triangles = [vertices];

  for (let d = 0; d < depth; d++) {
    let newTriangles = [];
    for (let i = 0; i < triangles.length; i++) {
      const currentTriangle = triangles[i];

      // calculates midpoints for each side
      const mid1 = [(currentTriangle[0] + currentTriangle[3]) / 2, (currentTriangle[1] + currentTriangle[4]) / 2, (currentTriangle[2] + currentTriangle[5]) / 2];
      const mid2 = [(currentTriangle[3] + currentTriangle[6]) / 2, (currentTriangle[4] + currentTriangle[7]) / 2, (currentTriangle[5] + currentTriangle[8]) / 2];
      const mid3 = [(currentTriangle[6] + currentTriangle[0]) / 2, (currentTriangle[7] + currentTriangle[1]) / 2, (currentTriangle[8] + currentTriangle[2]) / 2];

      // creates new triangles from the midpoints
      newTriangles.push([
          currentTriangle[0], currentTriangle[1], currentTriangle[2],
          mid1[0], mid1[1], mid1[2],
          mid3[0], mid3[1], mid3[2]
      ]);
      newTriangles.push([
          mid1[0], mid1[1], mid1[2],
          currentTriangle[3], currentTriangle[4], currentTriangle[5],
          mid2[0], mid2[1], mid2[2]
      ]);
      newTriangles.push([
          mid3[0], mid3[1], mid3[2],
          mid2[0], mid2[1], mid2[2],
          currentTriangle[6], currentTriangle[7], currentTriangle[8]
      ]);
    }
    triangles = newTriangles;
  }
  // makes the multiples arrays of traingles into a single array
  return triangles.flat();
}


let shader, buffer
shell.on('gl-init', function () {
  const gl = shell.gl
  subdividedVertices = divideTriangle(initialVertices, depth); 

  shader = createShader(gl, `
    attribute vec3 position;
    void main() {
      gl_Position =  vec4(position, 1.0);
    }
  `, `
    void main() {
      gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
    }
  `)
  buffer = createBuffer(gl, subdividedVertices);
})

shell.on('gl-render', function () {
  const gl = shell.gl
  shader.bind()
  buffer.bind()

  shader.attributes.position.pointer()
  gl.drawArrays(gl.TRIANGLES, 0, subdividedVertices.length / 3); 
})