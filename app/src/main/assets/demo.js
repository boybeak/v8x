const user = native.getUser();
console.log(user.name);
user.name = 'John';
user.location = {};
user.__proto__ = {};