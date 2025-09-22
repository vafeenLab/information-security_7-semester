import smtplib
from email.mime.text import MIMEText

# Server details
smtp_server = 'localhost'
smtp_port = 1025

# Email details
sender_email = 'sender@example.com'
receiver_email = 'receiver@example.com'
subject = 'Test Email from Python'
body = 'This is a test email sent to the local SMTP debugging server.'

# Create the email message
msg = MIMEText(body)
msg['Subject'] = subject
msg['From'] = sender_email
msg['To'] = receiver_email

try:
    with smtplib.SMTP(smtp_server, smtp_port) as server:
        server.send_message(msg)
    print("Email sent successfully to the debugging server.")
except Exception as e:
    print(f"Error sending email: {e}")
