#!/bin/sh -e

uname=$(whoami)

rm -rf /home/$uname/BKP

rm -rf /home/$uname/logs

rm -f BKP.tar.gz

exit 0
