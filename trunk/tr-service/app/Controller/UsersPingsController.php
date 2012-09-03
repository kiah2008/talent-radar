<?php
App::uses('AppController', 'Controller');

class UsersPingsController extends AppController {

	public $name = 'UsersPings';
	
	public $components = array('GCMNotification');
	
	public function beforeFilter() {
		parent::beforeFilter();
		
		$this->Auth->allow('app_addPing', 'app_replyPing', 'app_getPing');
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
				$userTo = $this->User->find('first', array('fields' => array('User.android_device_id'), 'conditions' => array('User.id' => $this->data['UsersPing']['user_to_id'])));
				if(!empty($userTo['User']['android_device_id'])) {
					$userFrom = $this->User->find('first', array('fields' => array('User.id', 'User.name', 'User.surname', 'User.username'), 'conditions' => array('User.id' => $this->data['UsersPing']['user_from_id'])));
					$messageNotification = str_replace('@@@USER@@@', $userFrom['User']['name'].' '.$userFrom['User']['surname'], __('@@@USER@@@ quiere contactarse con usted', true));
					$usersPing['UserFrom'] = $userFrom['User'];
					$response['result']['notification'] = $this->GCMNotification->send($userTo['User']['android_device_id'], NOTIFICATION_PING_ADDED, $messageNotification, $this->data['UsersPing']['user_to_id'], $usersPing);
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
			if($usersPing = $this->UsersPing->find('first', array('recursive' => -1, 'conditions' => array('UsersPing.id' => $this->data['UsersPing']['id'], 'UsersPing.user_to_id' => $this->data['UsersPing']['user_to_id']))))
			{
				if($this->UsersPing->save($this->data))
				{
					$usersPing['UsersPing']['response'] = $this->data['UsersPing']['response'];
					if($this->data['UsersPing']['response'] == PING_REJECTED_AND_BANNED) {
						$this->loadModel('UsersBanned');
						$data['UsersBanned']['user_from_id'] = $usersPing['UsersPing']['user_to_id'];
						$data['UsersBanned']['user_to_id'] = $usersPing['UsersPing']['user_from_id'];
						$this->UsersBanned->save($data);
					}
					else if ($this->data['UsersPing']['response'] == PING_ACCEPTED) {
						$this->loadModel('User');
						if($userFrom = $this->User->find('first', array('fields' => array('User.android_device_id'), 'conditions' => array('User.id' => $usersPing['UsersPing']['user_from_id'])))) {
							$userTo = $this->User->find('first', array('fields' => array('User.id', 'User.name', 'User.surname', 'User.username'), 'conditions' => array('User.id' => $usersPing['UsersPing']['user_to_id'])));
							$messageNotification = str_replace('@@@USER@@@', $userTo['User']['name'].' '.$userTo['User']['surname'], __('@@@USER@@@ acepto contactarse con usted', true));
							$usersPing['UserTo'] = $userTo['User'];
							$response['result']['notification'] = $this->GCMNotification->send($userFrom['User']['android_device_id'], NOTIFICATION_PING_ACCEPTED, $messageNotification, $usersPing['UsersPing']['user_from_id'], $usersPing);
						}
					}
					
					$response['result']['status'] = 'ok';
				}
			}
			
			$this->set('response', $response);
		}
		else
		{
			$this->set('ids', $this->UsersPing->find('list', array('fields' => array('id', 'id'))));
			$this->loadModel('User');
			$this->set('users', $this->User->find('list', array('fields' => array('id', 'id'))));
			$this->set('responses', array(PING_ACCEPTED => 'Accept', PING_REJECTED => 'Reject', PING_REJECTED_AND_BANNED => 'Ban'));
		}
	}
	
	public function app_getPing() {
		if(!empty($this->data)) {
			$response['status'] = 'ok';
			$response['result']['status'] = 'error';
			
			if($usersPing = $this->UsersPing->find('first', array('fields' => array('UsersPing.*', 'UserFrom.id', 'UserFrom.username', 'UserFrom.name', 'UserFrom.surname'), 'conditions' => array('UsersPing.id' => $this->data['UsersPing']['id'], 'UsersPing.user_to_id' => $this->data['UsersPing']['user_to_id']))))
			{
				$response['result'] = $usersPing;
				$response['result']['status'] = 'ok';
			}
			
			$this->set('response', $response);
		}
		else
		{
			$this->set('ids', $this->UsersPing->find('list', array('fields' => array('id', 'id'))));
			$this->loadModel('User');
			$this->set('users', $this->User->find('list', array('fields' => array('id', 'id'))));
		}
	}
}
