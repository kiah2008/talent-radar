<?php
App::uses('Model', 'Model');

class UsersJob extends AppModel {
	public $name = 'UsersJob';
	
	public $belongsTo = array(
    							'User'
							  );
}
