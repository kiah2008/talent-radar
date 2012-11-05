<?php
App::uses('Model', 'Model');

class UsersBanned extends AppModel {
	public $name = 'UsersBanned';
	
	public $useTable = 'users_banned';
	
	public $belongsTo = array(
    							'UserFrom' => array('className' => 'User', 'foreignKey' => 'user_from_id', 'fields' => array('name', 'surname', 'username', 'picture'))
							  );
}
