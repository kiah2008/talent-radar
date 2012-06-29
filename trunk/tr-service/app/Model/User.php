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

	public $validate = array();						  
	
	public function __construct($id = false, $table = null, $ds = null) {
		parent::__construct($id, $table, $ds);
		$this->validate = array(
    							'username' => array(
									'rule' => 'notEmpty',
									'message' => __('Required Field', true)
		                        ),
								'email' => array(
			                        'rule1' => array(
			                                             'rule' => 'notEmpty',
		                        						 'required' => true,
			                                             'message' => __('Required Field', true)
			                                          ),
									'rule2' => array(
			                                             'rule' => 'email',
			                                             'message' => __('Invalid Email', true)
			                                          ),
			                        'rule3' => array(
			                                           'rule' => 'isUnique',
			                                           'message' => __('Email is already in system', true)
			                                          )
		                        ),
		                        
		                        'password'	=> array(
									'rule' => 'notEmpty',
	                            	'message' => __('Required Field', true)
			                    ),
    						);
	}						  

}
