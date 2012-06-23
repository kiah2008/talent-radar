<?php
App::uses('Model', 'Model');

class User extends AppModel {
	public $name = 'User';
	
	public $hasMany = array(
    							'UsersJob',
								'UsersSkill',
								//'UsersMessage',
								'UsersStudy' 
							  );
							 
	public $validate = array(
    							'username' => array(
									'notEmpty' => array(
			                                             'rule' => 'notEmpty',
			                                             'message' => 'Required Field'
			                                          )
		                        ),
								'email' => array(
			                        'notEmpty',
									'email' => array(
			                                             'rule' => 'email',
			                                             'message' => 'Invalid Email'
			                                          ),
			                        'unique' => array(
			                                           'rule' => 'isUnique',
			                                           'message' => 'Email is already in system'
			                                          )
		                        ),
		                        
		                        'password'	=> array(
        							'notEmpty',
					        		array(
										'rule' => 'notEmpty',
		                            	'message' => 'Required Field'
		                        	),
                        			 ),
		                        'password_old_db' 	=> array(
									'notEmpty',
					        		array(
										'rule' => 'notEmpty',
		                            	'message' => 'Required Field'
		                        	),
                        		),
    						);
}
