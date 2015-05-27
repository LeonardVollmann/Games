var WIDTH = 600;
var HEIGHT = 600;
var MARGIN = 2;
var CELLSIZE = 20;
var NUM_COLS = Math.floor(WIDTH / CELLSIZE);
var NUM_ROWS = Math.floor(HEIGHT / CELLSIZE);

var KEY_UP = 38;
var KEY_DOWN = 40;
var KEY_LEFT = 37;
var KEY_RIGHT = 39;

var canvas;
var context;
var keystate;

var snake;
var apple;

var directionStack;

function randomIntInRange(min, max) {
    return Math.floor(Math.random() * (max - min) + min);
}

function placeApple() {
	apple = [randomIntInRange(0, NUM_ROWS), randomIntInRange(0, NUM_COLS)];
}

function expandSnake() {
	var tail = snake[snake.length - 1];
	var offset = tail[2] === 0 || tail[2] === 2 ? CELLSIZE : -CELLSIZE;

	var newTail;
	if (tail[2] === 0 || tail[2] === 1) {
		newTail = [tail[0] + offset, tail[1], tail[2], tail[2]];
	} else {
		newTail = [tail[0], tail[1] + offset, tail[2], tail[2]];
	}

	snake.push(newTail);
}

function init() {
	snake = [];
	snake[0] = [WIDTH / 2, HEIGHT / 2, randomIntInRange(0, 3)];
	placeApple();
	directionStack = [];
}

function move() {
	var oncell = false;
	if (snake[0][0] % CELLSIZE === 0 && snake[0][1] % CELLSIZE === 0) {
		oncell = true;
	}

	for (var i = 0; i < snake.length; i++) {
		if (oncell && i != 0) {
			snake[i][2] = snake[i][3];
			snake[i][3] = snake[i - 1][2];
		}

		if (snake[i][2] === 0) {
			snake[i][0]--;
		} else if (snake[i][2] === 1) {
			snake[i][0]++;
		} else if (snake[i][2] === 2) {
			snake[i][1]--;
		} else if (snake[i][2] === 3) {
			snake[i][1]++;
		}
	} 
}

function updateDirection() {
	if (keystate[KEY_UP] && snake[0][2] != 1) {
		directionStack.push(0);
		delete keystate[KEY_UP];
	} else if (keystate[KEY_DOWN] && snake[0][2] != 0) {
		directionStack.push(1);
		delete keystate[KEY_DOWN];
	} else if (keystate[KEY_LEFT] && snake[0][2] != 3) {
		directionStack.push(2);
		delete keystate[KEY_LEFT];
	} else if (keystate[KEY_RIGHT] && snake[0][2] != 2) {
		directionStack.push(3);
		delete keystate[KEY_RIGHT];
	}

	if (snake[0][0] % CELLSIZE === 0 && snake[0][1] % CELLSIZE === 0 && directionStack.length != 0) {
		snake[0][2] = directionStack.pop();
		snake[0][3] = snake[0][2];
	}
}

function checkCollisions() {
	var row = Math.floor(snake[0][0] / CELLSIZE);
	var col = Math.floor(snake[0][1] / CELLSIZE);
	if (row === apple[0] && col === apple[1]) {
		placeApple();
		expandSnake();
	}

	if (row < 0 || row === NUM_ROWS || col < 0 || col === NUM_COLS) {
		return init();
	}

	if (snake[0][0] % CELLSIZE === 0 && snake[0][1] % CELLSIZE === 0) {
		for (var i = 1; i < snake.length; i++) {
			var row1 = snake[i][0] / CELLSIZE;
			var col1 = snake[i][1] / CELLSIZE;

			if (row === row1 && col === col1) {
				return init();
			}
		}
	}
}

function update() {
	move();
	updateDirection();
	checkCollisions();
}

function render() {
	context.fillStyle = "#000000";
	context.fillRect(0, 0, WIDTH + MARGIN * 2, HEIGHT + MARGIN * 2);

	context.fillStyle = "#ffffff";
	context.fillRect(MARGIN, MARGIN, WIDTH, HEIGHT);

	context.fillStyle = "#00ff00";
	for (var i = 0; i < snake.length; i++) {
		context.fillRect(snake[i][1] + MARGIN, snake[i][0] + MARGIN, CELLSIZE, CELLSIZE);
	}

	context.fillStyle = "#ff0000";
	context.fillRect(apple[1] * CELLSIZE + MARGIN, apple[0] * CELLSIZE + MARGIN, CELLSIZE, CELLSIZE);
}

window.onload = function() {
	canvas = document.getElementById("canvas");
	canvas.width = WIDTH + MARGIN * 2;
	canvas.height = HEIGHT + MARGIN * 2;
	context = canvas.getContext("2d");

	keystate = [];
	var keydown = function(event) {
		keystate[event.keyCode] = true;
	}

	var keyup = function(event) {
		delete keystate[event.keyCode];
	}

	addEventListener("keydown", keydown);
	addEventListener("keyup", keyup);

	init();

	var loop = function() {
		update();
		render();
	}

	window.setInterval(loop, 3);
}