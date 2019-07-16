extends KinematicBody2D

# Declare member variables here. Examples:
# var a = 2
# var b = "text"
enum STATES { IDLE, FOLLOW }
var _state = null
const SPEED = 75
var health = 100
var velocity = Vector2()
var anim = "down"
var path = []
var target_point_world = Vector2()
var target_position = Vector2()

# Called when the node enters the scene tree for the first time.
func _ready():
	_change_state(STATES.FOLLOW)
	print("path" + str(path))

func get_target_pos():
	return get_parent().get_parent().get_node("Player").position

func _change_state(new_state):
	if new_state == STATES.FOLLOW:
		target_position = get_target_pos()
		path = get_parent().get_parent().get_node('TileMap/CollisionLayer')._get_path(position, get_target_pos())
		if not path or len(path) == 1:
			_change_state(STATES.IDLE)
			return
		# The index 0 is the starting cell
		# we don't want the character to move back to it in this example
		target_point_world = path[1]
	_state = new_state


func _process(delta):
	if not _state == STATES.FOLLOW:
		return
	var arrived_to_next_point = move_to(target_point_world)
	print(arrived_to_next_point)
	if arrived_to_next_point:
		path.remove(0)
		if len(path) == 0:
			_change_state(STATES.IDLE)
			return
		target_point_world = path[0]
	$AnimatedSprite.play(anim)
func move_to(world_position):
	var MASS = 10.0
	var ARRIVE_DISTANCE = 10.0

	var desired_velocity = (world_position - position).normalized()
	move_and_slide(desired_velocity*SPEED)
	return position.distance_to(world_position) < ARRIVE_DISTANCE
