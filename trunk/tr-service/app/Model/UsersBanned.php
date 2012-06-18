<?php
App::uses('Model', 'Model');

class UsersLanguage extends AppModel {
	public $name = 'UsersLanguage';
	
	public $belongsTo = array(
    							'User'
							  );
}
