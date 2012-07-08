<?php
App::uses('Model', 'Model');

class UsersOnline extends AppModel {
	public $name = 'UsersOnline';
	public $useTable = 'users_online';
	
	public $belongsTo = array(
    							'User'
							  );
	
							  
	public $validate = array();						  
	
	public function __construct($id = false, $table = null, $ds = null) {
		parent::__construct($id, $table, $ds);
		$this->validate = array(
    							'latitude' => array(
			                        'rule1' => array(
			                                             'rule' => 'notEmpty',
		                        						 'required' => true,
			                                             'message' => __('Required Field', true)
			                                          ),
									'rule2' => array(
			                                             'rule' => 'numeric',
			                                             'message' => __('Invalid Value', true)
			                                          ),
		                       	),
		                       	'longitude' => array(
			                        'rule1' => array(
			                                             'rule' => 'notEmpty',
		                        						 'required' => true,
			                                             'message' => __('Required Field', true)
			                                          ),
									'rule2' => array(
			                                             'rule' => 'numeric',
			                                             'message' => __('Invalid Value', true)
			                                          ),
		                       	)
		                    	);
	}
}
