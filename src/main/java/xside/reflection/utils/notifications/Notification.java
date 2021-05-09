package xside.reflection.utils.notifications;

import java.util.ArrayList;

public class Notification {
	private ArrayList<NotificationUtil> notifications;
	
	public Notification() {
		super();
		this.notifications = new ArrayList<NotificationUtil>();
	}
	
	public void addNotification(final NotificationType type, final String message, final int stayTime) {
		this.notifications.add(new NotificationUtil(type, message, stayTime));
	}

	public ArrayList<NotificationUtil> getNotifications() {
		return this.notifications;
	}
}
