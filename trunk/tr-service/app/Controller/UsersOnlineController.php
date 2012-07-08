<?php
App::uses('AppController', 'Controller');

class UsersOnlineController extends AppController {

	public $name = 'UsersOnline';

	public function beforeFilter() {
		parent::beforeFilter();
		
		$this->Auth->allow('app_shareLocation', 'app_getUsers');
	}
	
	public function app_shareLocation() {
		if(!empty($this->data)) {
			$response['status'] = 'ok';
			$response['result']['status'] = 'error';
			
			$this->UsersOnline->set($this->data);	
			if($ok = ($invalidFields = $this->UsersOnline->invalidFields()) ? false : true)
			{
				if($userAlreadyOnline = $this->UsersOnline->find('first', array('conditions' => array('UsersOnline.user_id' => $this->data['UsersOnline']['user_id'])))) {
					$this->request->data['UsersOnline']['id'] = $userAlreadyOnline['UsersOnline']['id'];
				}
				
				if($ok = $this->UsersOnline->save($this->data)) {
					$response['result']['status'] = 'ok';
				}
			}

			if(!$ok) {
				$response['result']['invalidFields'] = $invalidFields;
			}

			$this->set('response', $response);
		}
		else {
			$this->loadModel('User');
			$this->set('users', $this->User->find('list', array('fields' => array('id', 'id'))));
		}
	}
	
	public function app_getUsers() {
		if(!empty($this->data)) {
			$response['status'] = 'ok';
			$response['result']['status'] = 'error';
			
			$this->UsersOnline->set($this->data);	
			if($ok = ($invalidFields = $this->UsersOnline->invalidFields()) ? false : true)
			{
				$conditions['sqrt(pow(69.1 * (UsersOnline.latitude - '.$this->data['UsersOnline']['latitude'].'), 2) + pow(53.0 * (UsersOnline.longitude - '.$this->data['UsersOnline']['latitude'].'), 2)) <'] = (1/1.609344); //Calculate if distance is lower than input. (Convertion miles to kilometres)
				$conditions['UsersOnline.user_id <>'] = $this->data['UsersOnline']['user_id'];
				$conditions['TIMEDIFF(ADDTIME(UsersOnline.modified, UsersOnline.duration), NOW()) >'] = 0;
				$response['result']['users'] = $this->UsersOnline->find('all', array('conditions' => $conditions));
				$response['result']['status'] = 'ok';
			}
			
			if(!$ok) {
				$response['result']['invalidFields'] = $invalidFields;
			}
			
			$this->set('response', $response);
		}
		else {
			$this->loadModel('User');
			$this->set('users', $this->User->find('list', array('fields' => array('id', 'id'))));
		}
	}
}
