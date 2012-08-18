<?php
App::uses('AppController', 'Controller');

class TestsController extends AppController {

	public $name = 'Tests';
	
	public $uses = array('User');
	
	public $components = array('GCMNotification');

	public function beforeFilter() {
		parent::beforeFilter();
		
		$this->Auth->allow('app_sendNotificationByUserId', 'app_sendNotificationByAndroidDeviceId');
	}
	
	public function app_sendNotificationByUserId() {
		if(!empty($this->data)) {
			$response['status'] = 'ok';
			$response['result']['status'] = 'error';
			
			$user = $this->User->read(null, $this->data['User']['id']);
			if(!empty($user['User']['android_device_id'])) {
				$response['result']['status'] = 'ok';
				$response['result']['notification'] = $this->GCMNotification->send($user['User']['android_device_id'], $this->data['User']['message']);
			}
			else
			{
				$response['result']['message'] = 'This user does not have android id associated';
			}
			
			$this->set('response', $response);
		}
		else {
			$this->set('ids', $this->User->find('list', array('fields' => array('id', 'id'))));
		}
	}
	
	public function app_sendNotificationByAndroidDeviceId() {
		if(!empty($this->data)) {
			$response['status'] = 'ok';
			$response['result']['status'] = 'ok';
			
			$response['result']['notification'] = $this->GCMNotification->send($this->data['User']['android_device_id'], $this->data['User']['message']);
			$this->set('response', $response);
		}
	}
	
}
