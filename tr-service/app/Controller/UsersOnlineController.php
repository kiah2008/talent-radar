<?php
App::uses('AppController', 'Controller');

class UsersOnlineController extends AppController {

	public $name = 'UsersOnline';

	public function beforeFilter() {
		parent::beforeFilter();
		
		$this->Auth->allow('app_shareLocation');
	}
	
	public function app_shareLocation() {
		if(!empty($this->data)) {
			$response['status'] = 'error';
			$response['message'] = __('', true);
			
			$this->UsersOnline->set($this->data);
			if($ok = $this->UsersOnline->validates())
			{
				if($userAlreadyOnline = $this->UsersOnline->find('first', array('conditions' => array('UsersOnline.user_id' => $this->data['UsersOnline']['user_id'])))) {
					$this->request->data['UsersOnline']['id'] = $userAlreadyOnline['UsersOnline']['id'];
				}
				
				if($ok = $this->UsersOnline->save($this->data)) {
					$response['status'] = 'ok';
					$response['message'] = __('', true);
				}
			}

			if(!$ok) {
				$response['message'] = __('', true);
				$response['content'] = $this->UsersOnline->invalidFields();
			}

			$this->set('response', $response);
		}
		else {
			$this->loadModel('User');
			$this->set('users', $this->User->find('list', array('fields' => array('id', 'id'))));
		}
	}
}
