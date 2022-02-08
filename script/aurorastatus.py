import json
from urllib.request import urlopen


def fetch(url):
    response = urlopen(url)
    data = json.loads(response.read())
    last = data[-1]
    return last


class AuroraStatus:
    pred_url = "https://services.swpc.noaa.gov/json/geospace/geospace_pred_est_kp_1_hour.json"
    rt_url = "https://services.swpc.noaa.gov/json/planetary_k_index_1m.json"
    
    def __init__(self):
        self.pred_kp = 0
        self.rt_kp = 0

    def update(self):
        self.pred_kp = fetch(self.pred_url).get('k')
        self.rt_kp = fetch(self.rt_url).get('kp_index')

    def build_notify(self, typ, kp):
        if typ == 'pred':
            pref = 'WARNING (1 hour): '
            mod = 'predicted '
        elif typ == 'rt':
            mod = 'estimated '
            pref =  'ALERT (NOW): '
        if kp >= 1 and kp < 3:
            return pref + 'low activity ' + mod + 'Kp = ' + str(kp)
        if kp >= 3 and kp < 5:
            return pref + 'moderate activity ' + mod + 'Kp = ' + str(kp)
        if kp >= 5 and kp < 6:
            return pref + 'G1 geomagnetic storm ' + mod + '!'
        if kp >= 6 and kp < 7:
            return pref + 'G2 geomagnetic storm ' + mod + '!'
        if kp >= 7 and kp < 8:
            return pref + 'G3 geomagnetic storm ' + mod + '!'
        if kp >= 8 and kp < 9:
            return pref + 'G4 geomagnetic storm ' + mod + '!'
        if kp >= 9:
            return pref + 'G5 geomagnetic storm ' + mod + '!'

    def pred_notify(self):
        return self.build_notify('pred', self.pred_kp)

    def rt_notify(self, thresh):
        alert = thresh <= self.rt_kp
        return alert, self.build_notify('rt', self.rt_kp)
