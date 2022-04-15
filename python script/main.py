import argparse
import time
import chime
from aurorastatus import AuroraStatus
from plyer import notification


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Configure alert system')
    parser.add_argument('-t', '--time', type=int, help='length of interval (minutes)')
    parser.add_argument('-k', '--kp', type=int, default=10, help='min Kp index for audible alert')
    minute = parser.parse_args().time
    minKp = parser.parse_args().kp
    stat = AuroraStatus()

    while True:
        stat.update()
        pred_note = stat.pred_notify()
        alert, rt_note = stat.rt_notify(minKp)
        if pred_note:
            notification.notify(title='auroralert!', message=pred_note)
        if rt_note:
            notification.notify(title='auroralert!', message=rt_note)
            if alert:
                chime.info()     
        time.sleep(minute*60)

