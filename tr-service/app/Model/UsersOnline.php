<?php
App::uses('Model', 'Model');

class UsersOnline extends AppModel {
	public $name = 'UsersOnline';
	public $useTable = 'users_online';
	
	public $belongsTo = array(
    							'User'
							  );
	
}
