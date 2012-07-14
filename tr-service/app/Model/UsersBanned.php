<?php
App::uses('Model', 'Model');

class UsersBanned extends AppModel {
	public $name = 'UsersBanned';
	
	public $useTable = 'users_banned';
	
	/*public $belongsTo = array(
    							'User'
							  );*/
}
