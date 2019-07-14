extends KinematicBody2D

# Declare member variables here. Examples:
# var a = 2
# var b = "text"
const SPEED = 200
var velocity = Vector2()
var player_pos
var curr_pos
var anim = "down"
onready var player = get_tree().get_root().get_node("World/Player")
onready var map = get_tree().get_root().get_node("World/TileMap/BackgroundLayer")
onready var sprite = get_node("sprite")



# Called when the node enters the scene tree for the first time.
func _ready():
	pass # Replace with function body.

func sqr(num):
	return num*num

func get_distance(initial, dest):
	#distance heuristic for A*
	return abs(sqrt(sqr(dest.x - initial.x) + sqr(dest.y - initial.y)))

func _physics_process(delta):
	#update data
	player_pos = map.world_to_map(player.global_position)
	curr_pos = map.world_to_map(global_position)
	#print(get_distance(curr_pos, player_pos))
	
	
	#End logisitics
	velocity = velocity.normalized()
	move_and_collide(velocity*SPEED)
	$AnimatedSprite.play(anim)
	pass