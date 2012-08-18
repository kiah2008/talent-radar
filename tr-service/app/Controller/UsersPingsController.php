<?php
App::uses('AppController', 'Controller');

class UsersPingsController extends AppController {

	public $name = 'UsersPings';
	
	public $components = array('GCMNotification');
	
	public function beforeFilter() {
		parent::beforeFilter();
		
		$this->Auth->allow('app_addPing', 'app_replyPing');
	}
	
	public function app_addPing()
	{
		if(!empty($this->data))
		{
			$response['status'] = 'ok';
			$response['result']['status'] = 'error';
			
			if($usersPing = $this->UsersPing->save($this->data))
			{
				$response['result']['status'] = 'ok';
				$response['result']['UsersPing'] = $usersPing['UsersPing'];
				
				$this->loadModel('User');
				$userTo = $this->User->read(null, $this->data['UsersPing']['user_to_id']);
				if(!empty($userTo['User']['android_device_id'])) {
					$response['result']['notification'] = $this->GCMNotification->send($userTo['User']['android_device_id'], __('Hola', true));
				}
			}
			
			$this->set('response', $response);
		}
		else
		{
			$this->loadModel('User');
			$this->set('users', $this->User->find('list', array('fields' => array('id', 'id'))));
		}
	}
	
	public function app_replyPing() {
		if(!empty($this->data))
		{
			$response['status'] = 'ok';
			$response['result']['status'] = 'error';
			
			if($this->UsersPing->save($this->data))
			{
				$usersPing = $this->UsersPing->read(null, $this->data['UsersPing']['id']);
 				if($this->data['UsersPing']['response'] == 3) {
					$this->loadModel('UsersBanned');
					$data['UsersBanned']['user_from_id'] = $usersPing['UsersPing']['user_to_id'];
					$data['UsersBanned']['user_to_id'] = $usersPing['UsersPing']['user_from_id'];
					$this->UsersBanned->save($data);
				}
				
				$response['result']['status'] = 'ok';
			}
			
			$this->set('response', $response);
		}
		else
		{
			$this->set('ids', $this->UsersPing->find('list', array('fields' => array('id', 'id'))));
			$this->set('responses', array(1 => 'Accept', 2 => 'Reject', 3 => 'Ban'));
		}
	}
}
