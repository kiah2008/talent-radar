<?php
App::uses('Model', 'Model');

class UsersBanned extends AppModel {
	public $name = 'UsersBanned';
	
	public $belongsTo = array(
    							'User',
								'Language'
							  );
}
