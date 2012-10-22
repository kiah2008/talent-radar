<?php
App::uses('AppController', 'Controller');

class UsersOnlineController extends AppController {

	public $name = 'UsersOnline';

	public function beforeFilter() {
		parent::beforeFilter();
		
		$this->Auth->allow('app_shareLocationAndGetUsers');
	}
	
	public function _app_getUsers($data) {
		$conditions['sqrt(pow(69.1 * (UsersOnline.latitude - '.$data['UsersOnline']['latitude'].'), 2) + pow(53.0 * (UsersOnline.longitude - '.$data['UsersOnline']['latitude'].'), 2)) <'] = (Configure::read('DistanceToGetUsers')/1.609344); //Calculate if distance is lower than input. (Convertion miles to kilometres)
		$conditions['UsersOnline.user_id <>'] = $data['UsersOnline']['user_id'];
		$conditions['TIMEDIFF(ADDTIME(UsersOnline.modified, SEC_TO_TIME(UsersOnline.duration)), NOW()) >'] = 0;
					
		return $this->UsersOnline->find('all', array('conditions' => $conditions));
	}
	
	public function app_shareLocationAndGetUsers() {
		if(!empty($this->data)) {
			$response['status'] = 'ok';
			$response['result']['status'] = 'error';
			
			$this->UsersOnline->set($this->data);	
			if($ok = ($invalidFields = $this->UsersOnline->invalidFields()) ? false : true)
			{
				$this->loadModel('User');
				$user = $this->User->find('first', array('recursive' => -1, 'conditions' => array('User.id' => $this->data['UsersOnline']['user_id'])));
				
				if($user) {
					$userAlreadyOnline = $this->UsersOnline->find('first', array('conditions' => array('UsersOnline.user_id' => $this->data['UsersOnline']['user_id'])));
					
					if($user['User']['show_in_searches']) {
						if($userAlreadyOnline) {
							$this->request->data['UsersOnline']['id'] = $userAlreadyOnline['UsersOnline']['id'];
						}
						
						if($ok = $this->UsersOnline->save($this->data)) {
							$response['result']['status'] = 'ok';
							$response['result']['users'] = $this->_app_getUsers($this->data);
						}
						
						if(!$ok) {
							$response['result']['invalidFields'] = $invalidFields;
						}
					}
					else {
						if($userAlreadyOnline) {
							$this->UsersOnline->delete($userAlreadyOnline['UsersOnline']['id']);
						}
						
						$response['result']['status'] = 'ok';
						$response['result']['users'] = $this->_app_getUsers($this->data);
					}
				}
			}
			
			$this->set('response', $response);
		}
		else {
			$this->loadModel('User');
			$this->set('users', $this->User->find('list', array('fields' => array('id', 'id'))));
		}
	}
}
