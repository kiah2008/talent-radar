<?php
App::uses('Model', 'Model');

class User extends AppModel {
	public $name = 'User';
	
	public $hasMany = array(
    							'UsersJob',
								'UsersSkill',
								'UsersMessage',
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
		                        
		                        'password_original'	=> array(
        							'notEmpty',
					        		array(
										'rule' => 'notEmpty',
		                            	'message' => 'Required Field'
		                        	),
                        			 ),
		                        'password_confirm' 	=> array(
			        				'notEmpty',
					        		array(
										'rule' => 'notEmpty',
		                            	'message' => 'Required Field'
		                        	),
		        					'identicalFieldValues' => array(
					        										'rule' => array('identicalFieldValues', 'password_original' ),
					        										'message' => 'Passwords not are the same'
									  								)
					            	),
					            'password_old_db' 	=> array(
									'notEmpty',
					        		array(
										'rule' => 'notEmpty',
		                            	'message' => 'Required Field'
		                        	),
                        		),
       							'password_old' 	=> array(
				        			'notEmpty',
					        		array(
										'rule' => 'notEmpty',
		                            	'message' => 'Required Field'
		                        	),
        							'identicalFieldValues' => array(
			        										'rule' => array('identicalFieldValues', 'password_old_db' ),
			        										'message' => 'Previous password is wrong'
							  								)
			            		),
    						);
}
