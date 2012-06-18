<?php
App::uses('Model', 'Model');

class UsersStudy extends AppModel {
	public $name = 'UsersStudy';
	
	public $belongsTo = array(
    							'User'
							  );
}
