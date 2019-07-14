extends KinematicBody2D

# class member variables go here, for example:
# var a = 2
# var b = "textvar"
const SPEED = 125
var motion = Vector2()
onready var sprite = get_node("sprite")
var anim = "down"

func _ready():
	# Called when the node is added to the scene for the first time.
	# Initialization here
	pass
	
func _physics_process(delta):
	if Input.is_action_pressed("ui_down"):
		motion.y = SPEED
		anim = "down"	
	elif Input.is_action_pressed("ui_up"):
		motion.y = -SPEED
		anim = "up"
	else:
		motion.y = 0
	if Input.is_action_pressed("ui_right"):
		motion.x = SPEED
		anim = "right"
	elif Input.is_action_pressed("ui_left"):
		motion.x = -SPEED
		anim = "left"	
	else:
		motion.x = 0
	if motion.x == 0 and motion.y == 0:
		sprite.playing = false
		sprite.set_frame(0)
	sprite.play(anim)
	move_and_slide(motion)	
	pass