<?php
App::uses('Model', 'Model');

class UsersSkill extends AppModel {
	public $name = 'UsersSkill';
	
	public $belongsTo = array(
    							'User'
							  );
	
}
