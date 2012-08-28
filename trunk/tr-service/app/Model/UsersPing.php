<?php
App::uses('Model', 'Model');

class UsersPing extends AppModel {
	public $name = 'UsersPing';
	
	public $belongsTo = array(
    							'UserFrom' => array('className' => 'User', 'foreignKey' => 'user_from_id')
							 );
}
