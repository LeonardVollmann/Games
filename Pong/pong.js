var WIDTH = 800;
var HEIGHT = 600;
var UP = 38;
var DOWN = 40;
var canvas;
var context;
var keystate;
var score_player;
var score_opponent;

var player = {
	x: null,
	y: null,
	width: 20,
	height: 100,
	speed: 7,

	update: function() {
		if (keystate[DOWN]) { 
			if (this.y + this.height < HEIGHT) {
				this.y += this.speed;
			} else {
				this.y = HEIGHT - this.height;
			}
		}

		if (keystate[UP]) {
			if (this.y > 0) {
				this.y -= this.speed;
			} else {
				this.y = 0;
			}
		}
	},

	render : function() {
		context.fillRect(this.x, this.y, this.width, this.height);
	}
};

var opponent = {
	x: null,
	y: null,
	width: 20,
	height: 100,
	speed: 4,

	update: function() {
		var ballDestY = ball.y + ball.vel.y;
		var optimalPos = ballDestY - this.height / 2;
		var delta = optimalPos - this.y;
		
		if (Math.abs(delta) > this.speed) {
			this.y += delta > 0 ? this.speed : -this.speed;
		} else {
			this.y += delta;
		}

		if (this.y < 0) {
			this.y = 0;
		} else if (this.y + this.height > HEIGHT) {
			this.y = HEIGHT - this.height;
		}
	},

	render : function() {
		context.fillRect(this.x, this.y, this.width, this.height);
	}
};

var ball = {
	x: null,
	y: null,
	vel: null,
	size: 15,
	default_speed: 5,
	speed: 5,
	elasticity: 1.05,

	update: function() {
		this.x += this.vel.x;
		this.y += this.vel.y;

		var paddle = this.vel.x < 0 ? player : opponent;

		if (this.x < 0|| this.x + this.size > WIDTH) {
			this.serve(paddle);
			increaseScore(paddle);
		}

		if (this.y <= 0) {
			this.y = 0;
			this.vel.y *= -1;
		} else if (this.y + this.size >= HEIGHT) {
			this.y = HEIGHT - this.size;
			this.vel.y *= -1;
		}

		if (aabbIntersect(this.x, this.y, this.size, this.size, paddle.x, paddle.y, paddle.width, paddle.height)) {
			this.x = paddle === player ? player.x + player.width : opponent.x - this.size;
			this.speed *= this.elasticity;

			var normalizedY = (this.y + this.size - paddle.y) / (paddle.height + this.size);
			var angle = 0.25 * Math.PI * (2 * normalizedY - 1);

			this.vel.x = this.speed * Math.cos(angle) * (paddle === player ? 1 : -1);
			this.vel.y = this.speed * Math.sin(angle);
		}
	},

	render : function() {
		context.fillRect(this.x, this.y, this.size, this.size);
	},

	serve: function(paddle) {
		this.speed = this.default_speed;

		this.x = (WIDTH - this.size) / 2;
		this.y = (HEIGHT - this.size) / 2;

		var side = paddle === player ? -1 : 1;
		var maxAngle = 0.35 * Math.PI;
		var angle = getRandomInRange(-maxAngle, maxAngle);
		this.vel.x = this.speed * Math.cos(angle);
		this.vel.y = this.speed * Math.sin(angle);
	}
};

function main() {
	canvas = document.getElementById("canvas");
	canvas.width = WIDTH;
	canvas.height = HEIGHT;
	context = canvas.getContext("2d");
	context.font = "60pt Arial";
	document.body.appendChild(canvas);

	keystate = {};
	document.addEventListener("keydown", function(event) {
		keystate[event.keyCode] = true;
	});
	document.addEventListener("keyup", function(event) {
		delete keystate[event.keyCode];
	});

	init();

	var loop = function() {
		update();
		render();

		window.requestAnimationFrame(loop, canvas);
	};
	window.requestAnimationFrame(loop, canvas);
}

function init() {
	score_player = 0;
	score_opponent = 0;

	player.x = player.width;
	player.y = (HEIGHT - player.height) / 2;

	opponent.x = (WIDTH - player.width * 2);
	opponent.y = (HEIGHT - player.height) / 2;

	ball.x = (WIDTH - ball.size) / 2;
	ball.y = (HEIGHT - ball.size) / 2;
	ball.vel = {
		x: -ball.speed,
		y: 0
	};
}

function update() {
	ball.update();
	player.update();
	opponent.update();
}

function render() {
	context.fillStyle = "#000000";
	context.fillRect(0, 0, WIDTH, HEIGHT);

	context.fillStyle = "#ffffff";
	player.render();
	opponent.render();
	ball.render();

	var width = 5;
	var x = (WIDTH - width) / 2;
	var y = 0;
	var step = HEIGHT / 10;
	while (y < HEIGHT) {
		context.fillRect(x, y + step / 4, width, step / 2);
		y += step;
	}

	context.fillText(score_player, WIDTH / 2 - 100, HEIGHT / 2 + 30);
	context.fillText(score_opponent, WIDTH / 2 + 70, HEIGHT / 2 + 30);
}

function aabbIntersect(ax, ay, aw, ah, bx, by, bw, bh) {
	return ax < bx+bw && ay < by+bh && bx < ax+aw && by < ay+ah;
}

function getRandomInRange(min, max) {
    return Math.random() * (max - min) + min;
}


function increaseScore(paddle) {
	if (paddle === player) {
		score_opponent++;
	} else {
		score_player++;
	}
}

main();