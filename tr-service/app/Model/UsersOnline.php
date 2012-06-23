<?php
App::uses('Model', 'Model');

class UsersOnline extends AppModel {
	public $name = 'UsersOnline';
	
	public $belongsTo = array(
    							'User'
							  );
	
}
