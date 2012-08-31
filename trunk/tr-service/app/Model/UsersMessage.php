<?php
App::uses('Model', 'Model');

class UsersMessage extends AppModel {
	public $name = 'UsersMessage';
	
	public $order = array('UsersMessage.created' => 'ASC');
}
