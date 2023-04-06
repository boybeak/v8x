let obj = {};
console.log('hello');
console.log("has __proto__=", "__proto__"  in obj);

let map = new Map();
console.log("__proto__ in map=", "__proto__"  in map);
console.log("has __proto__ =", map.has("__proto__"));