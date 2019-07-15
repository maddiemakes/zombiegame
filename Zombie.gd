extends KinematicBody2D

# Declare member variables here. Examples:
# var a = 2
# var b = "text"
const SPEED = 200
var health = 100
var velocity = Vector2()
var player_pos
var curr_pos
var anim = "down"
onready var player = get_tree().get_root().get_node("World/Player")
onready var map = get_tree().get_root().get_node("World/TileMap/CollisionLayer")
var graph = []

# Called when the node enters the scene tree for the first time.
func _ready():
	#Generate map
	var reg = RegEx.new()
	reg.compile('wood_\\d+')
	var tile_name
	for i in range(0,99):
		graph.append([])
		for j in range(0,99):
			tile_name = map.tile_set.tile_get_name(map.get_cellv(Vector2(i,j)))
			if reg.search(tile_name):
				graph[i].append(1)
			else:
				graph[i].append(0)
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
	var tile_name = map.tile_set.tile_get_name(map.get_cellv(curr_pos))
	#Get Path
	
	#set velocity/anim
	
	#End logisitics
	velocity = velocity.normalized()
	move_and_collide(velocity*SPEED)
	$AnimatedSprite.play(anim)
	if velocity == Vector2.ZERO:
		$AnimatedSprite.playing = false
		$AnimatedSprite.set_frame(0)
	pass