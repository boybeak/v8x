const gl = canvas.getContext("webgl");

const vertexShader = `
attribute vec4 a_position;
void main () {
    gl_Position = a_position;
    gl_PointSize = 10.;
}
`;

const fragmentShader = `
    precision mediump float;
    void main () {
        gl_FragColor = vec4(1.0, 0.5, 1.0, 1.0);
    }
`;

const program = initWebGL(gl, vertexShader, fragmentShader);
gl.useProgram(program);

const a_position = gl.getAttribLocation(program, "a_position");
gl.vertexAttrib3f(a_position, 0.0, 0.0, 0.0);

gl.drawArrays(gl.POINTS, 0, 1);


/**
 *
 * @param {WebGL2RenderingContext} gl
 * @param {string} type
 * @param {string} source
 */
function createShader(gl, type, source) {
    let shader = gl.createShader(type);
    gl.shaderSource(shader, source);
    gl.compileShader(shader);
    let success = gl.getShaderParameter(shader, gl.COMPILE_STATUS);
    if (success) {
        return shader;
    }
    console.log(gl.getShaderInfoLog(shader));
    gl.deleteShader(shader);
}

/**
 *
 * @param {WebGL2RenderingContext} gl
 * @param {WebGLShader} vertexShader
 * @param {WebGLShader} fragmentShader
 */
function createProgram(gl, vertexShader, fragmentShader) {
    let program = gl.createProgram();
    gl.attachShader(program, vertexShader);
    gl.attachShader(program, fragmentShader);
    gl.linkProgram(program);
    let success = gl.getProgramParameter(program, gl.LINK_STATUS);
    if (success) {
        return program;
    }
    console.log(gl.getProgramInfoLog(program));
    gl.deleteProgram(program);
}

/**
 *
 * @param {WebGL2RenderingContext} gl
 * @param {string} vertexSource
 * @param {string} fragmentSource
 */
function initWebGL(gl, vertexSource, fragmentSource) {
    let vertexShader = createShader(gl, gl.VERTEX_SHADER, vertexSource);
    let fragmentShader = createShader(gl, gl.FRAGMENT_SHADER, fragmentSource);
    let program = createProgram(gl, vertexShader, fragmentShader);
    return program;
}


/**
 *
 * @param {WebGLRenderingContext} gl
 */
function createTexture(gl) {
    let texture = gl.createTexture();
    gl.bindTexture(gl.TEXTURE_2D, texture);
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR);
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR);
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE);
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE);
    // gl.generateMipmap(gl.TEXTURE_2D);
    return texture;
}

function createProjectionMat(l, r, t, b, n, f) {
    return [
        2 / (r - l), 0, 0, 0,
        0, 2 / (t - b), 0, 0,
        0, 0, 2 / (f - n), 0,
        -(r + l) / (r - l), -(t + b) / (t - b), -(f + n) / (f - n), 1
    ]
}


function createTranslateMat(tx, ty) {
    return [
        1, 0, 0, 0,
        0, 1, 0, 0,
        0, 0, 1, 0,
        tx, ty, 0, 1
    ]
}

function createRotateMat(rotate) {
    rotate = rotate * Math.PI / 180;
    const cos = Math.cos(rotate);
    const sin = Math.sin(rotate);
    return [
        cos, sin, 0, 0,
        -sin, cos, 0, 0,
        0, 0, 1, 0,
        0, 0, 0, 1
    ]
}

function createScaleMat(sx, sy) {
    return [
        sx, 0, 0, 0,
        0, sy, 0, 0,
        0, 0, 1, 0,
        0, 0, 0, 1
    ]
}