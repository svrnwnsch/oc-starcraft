Initiale besprechung am 17.05.2017
===================================
* Wie soll der Bot funktionieren.
** ein XCS für alle
** **ein XCS für jeden Einheitentyp**
** ein XCS für jede Einheit

* Welche Eingabewerte haben wir
** Absolute eigene Position
** Relative Positionen der k/l vielen nähesten Enemies/Allies inkl health type etc.
*** Relative Koordinaten gitter zu anderen Einheiten (möglicherweise die größe begrenzen)
*** System über Polarkoordinaten definieren
** Gesamtanzahl der eigenen und sichtbaren fremden Einheiten
** Insgesammter Killcount

* Mögliche Probleme:
** Terrain abbilden (es kann nicht überall hin gelaufen werden wasser etc) Fliegende einheiten können aber über wasser
** => Es gibt eine Erweiterung der API, welche die gesamte Karte scannt https://bitbucket.org/auriarte/bwta2

Actions
--------
* Pause
* Move in Direction (8?)
* Move to closest Enemy/Allie (of type...)
* Shoot at Enemy of type X with lowest health/ closest position
* Use special abilities


Rewards
--------
* Multistep Rewards
* +1 Rewards
** Laufe an eine mögliche Position
* +5 Rewards
* +10 Rewards
** Finde gegnerische Einheit
* +50 Rewards
** Füge Gengerische einheit Schaden zu (Mehr reward für mehr Schaden)
** Heile Befreundete Einheiten (Mehr reward für mehr Heal)
* +500 Rewards
** Zerstöre gegnerische Einheit
* Gewinne Spiel +5000

* -1 Rewards
** Lauf an eine unmögliche Stelle (Wasser, ausserhalb der Karte etc.)
** Verliere Mana (abhängig von wie viel Mana man verliert)
* -5 Rewards
** Werde von Gegnerische Einheit entdeckt
* -10 Rewards
* -50 Rewards
** Bekomme Schaden (abhängig von höhe des Schadens)
* -2000 Rewards
** Werde zerstört
* Verlier Spiel -5000

Vortrag Ideen
-------------
* Voitures können Minen legen, die Anzahl der minenen als Input verwenden. Reward findet deutlich später statt (wenn überhaupt). Taktik: Minen um Base legen oder engstellen der map. Time delayed reward?
* Kein Feedback von Tutoren, scheint alles zu passen.

