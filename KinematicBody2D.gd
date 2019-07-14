extends KinematicBody2D

# class member variables go here, for example:
# var a = 2
# var b = "textvar"
const SPEED = 125
const SENSITIVITY = 10
var motion = Vector2()
var mouse_pos
var cur_tile
onready var sprite = get_node("sprite")
onready var map = get_parent().get_node("TileMap").get_node("BackgroundLayer")
var anim = "down"

func is_deadzone():
	return abs(mouse_pos.x - position.x) < SENSITIVITY
	pass

func _ready():
	# Called when the node is added to the scene for the first time.
	# Initialization here
	pass
	
func _physics_process(delta):
	if Input.is_action_pressed("ui_down"):
		motion.y = SPEED
	elif Input.is_action_pressed("ui_up"):
		motion.y = -SPEED
	else:
		motion.y = 0
	if Input.is_action_pressed("ui_right"):
		motion.x = SPEED
	elif Input.is_action_pressed("ui_left"):
		motion.x = -SPEED
	else:
		motion.x = 0
	if motion.x == 0 and motion.y == 0:
		sprite.playing = false
		sprite.set_frame(0)
	mouse_pos = get_global_mouse_position()
	if mouse_pos.y > position.y:
		anim = "down"
	elif mouse_pos.y < position.y:
		anim = "up"
	if mouse_pos.x > position.x and !is_deadzone():
		anim= "right"
	elif mouse_pos.x < position.x and !is_deadzone():
		anim =  "left"
	cur_tile = map.world_to_map(position)
	#print(cur_tile)
	sprite.play(anim)
	move_and_slide(motion)	
	pass