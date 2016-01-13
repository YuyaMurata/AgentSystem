cd "C:/Users/kaeru/Documents/NetBeansProjects/AgentSystem/AgentSystem/logs/user10";
data="System-ALL_p[60s,1000ms,u100000,ag10,s1,st128,tMountType,L1000,ws100,w(100,10)].csv";
set datafile separator ",";
set xdata time;
set yrange [0:1000];
set timefmt "%Y-%m-%d %H:%M:%S";
set ticscale 0.2;
set term png size 32000,20000;
set output "gnuplot-png.png";
set style fill solid;
set multiplot layout 15,16;
set title "R#0000";
plot data using 1:17 with boxes lc rgb "red" notitle;
set title "R#0001";
plot data using 1:18 with boxes lc rgb "green" notitle;
set title "R#0002";
plot data using 1:19 with boxes lc rgb "blue" notitle;
set title "R#0003";
plot data using 1:20 with boxes lc rgb "cyan" notitle;
set title "R#0004";
plot data using 1:21 with boxes lc rgb "magenta" notitle;
set title "R#0005";
plot data using 1:22 with boxes lc rgb "orange" notitle;
set title "R#0006";
plot data using 1:23 with boxes lc rgb "purple" notitle;
set title "R#0007";
plot data using 1:24 with boxes lc rgb "khaki" notitle;
set title "R#0008";
plot data using 1:25 with boxes lc rgb "brown" notitle;
set title "R#0009";
plot data using 1:26 with boxes lc rgb "navy" notitle;
set title "R#0010";
plot data using 1:27 with boxes lc rgb "green" notitle;
set title "R#0011";
plot data using 1:28 with boxes lc rgb "green" notitle;
set title "R#0012";
plot data using 1:29 with boxes lc rgb "green" notitle;
set title "R#0013";
plot data using 1:30 with boxes lc rgb "green" notitle;
set title "R#0014";
plot data using 1:31 with boxes lc rgb "green" notitle;
set title "R#0015";
plot data using 1:32 with boxes lc rgb "green" notitle;
set title "R#0016";
plot data using 1:33 with boxes lc rgb "green" notitle;
set title "R#0017";
plot data using 1:34 with boxes lc rgb "green" notitle;
set title "R#0018";
plot data using 1:35 with boxes lc rgb "brown" notitle;
set title "R#0019";
plot data using 1:36 with boxes lc rgb "brown" notitle;
set title "R#0020";
plot data using 1:37 with boxes lc rgb "brown" notitle;
set title "R#0021";
plot data using 1:38 with boxes lc rgb "brown" notitle;
set title "R#0022";
plot data using 1:39 with boxes lc rgb "magenta" notitle;
set title "R#0023";
plot data using 1:40 with boxes lc rgb "magenta" notitle;
set title "R#0024";
plot data using 1:41 with boxes lc rgb "magenta" notitle;
set title "R#0025";
plot data using 1:42 with boxes lc rgb "magenta" notitle;
set title "R#0026";
plot data using 1:43 with boxes lc rgb "brown" notitle;
set title "R#0027";
plot data using 1:44 with boxes lc rgb "magenta" notitle;
set title "R#0028";
plot data using 1:45 with boxes lc rgb "green" notitle;
set title "R#0029";
plot data using 1:46 with boxes lc rgb "magenta" notitle;
set title "R#0030";
plot data using 1:47 with boxes lc rgb "green" notitle;
set title "R#0031";
plot data using 1:48 with boxes lc rgb "magenta" notitle;
set title "R#0032";
plot data using 1:49 with boxes lc rgb "magenta" notitle;
set title "R#0033";
plot data using 1:50 with boxes lc rgb "brown" notitle;
set title "R#0034";
plot data using 1:51 with boxes lc rgb "magenta" notitle;
set title "R#0035";
plot data using 1:52 with boxes lc rgb "magenta" notitle;
set title "R#0036";
plot data using 1:53 with boxes lc rgb "brown" notitle;
set title "R#0037";
plot data using 1:54 with boxes lc rgb "green" notitle;
set title "R#0038";
plot data using 1:55 with boxes lc rgb "khaki" notitle;
set title "R#0039";
plot data using 1:56 with boxes lc rgb "magenta" notitle;
set title "R#0040";
plot data using 1:57 with boxes lc rgb "purple" notitle;
set title "R#0041";
plot data using 1:58 with boxes lc rgb "khaki" notitle;
set title "R#0042";
plot data using 1:59 with boxes lc rgb "purple" notitle;
set title "R#0043";
plot data using 1:60 with boxes lc rgb "purple" notitle;
set title "R#0044";
plot data using 1:61 with boxes lc rgb "green" notitle;
set title "R#0045";
plot data using 1:62 with boxes lc rgb "brown" notitle;
set title "R#0046";
plot data using 1:63 with boxes lc rgb "khaki" notitle;
set title "R#0047";
plot data using 1:64 with boxes lc rgb "magenta" notitle;
set title "R#0048";
plot data using 1:65 with boxes lc rgb "khaki" notitle;
set title "R#0049";
plot data using 1:66 with boxes lc rgb "green" notitle;
set title "R#0050";
plot data using 1:67 with boxes lc rgb "khaki" notitle;
set title "R#0051";
plot data using 1:68 with boxes lc rgb "purple" notitle;
set title "R#0052";
plot data using 1:69 with boxes lc rgb "brown" notitle;
set title "R#0053";
plot data using 1:70 with boxes lc rgb "khaki" notitle;
set title "R#0054";
plot data using 1:71 with boxes lc rgb "khaki" notitle;
set title "R#0055";
plot data using 1:72 with boxes lc rgb "cyan" notitle;
set title "R#0056";
plot data using 1:73 with boxes lc rgb "khaki" notitle;
set title "R#0057";
plot data using 1:74 with boxes lc rgb "cyan" notitle;
set title "R#0058";
plot data using 1:75 with boxes lc rgb "cyan" notitle;
set title "R#0059";
plot data using 1:76 with boxes lc rgb "cyan" notitle;
set title "R#0060";
plot data using 1:77 with boxes lc rgb "brown" notitle;
set title "R#0061";
plot data using 1:78 with boxes lc rgb "brown" notitle;
set title "R#0062";
plot data using 1:79 with boxes lc rgb "purple" notitle;
set title "R#0063";
plot data using 1:80 with boxes lc rgb "cyan" notitle;
set title "R#0064";
plot data using 1:81 with boxes lc rgb "khaki" notitle;
set title "R#0065";
plot data using 1:82 with boxes lc rgb "cyan" notitle;
set title "R#0066";
plot data using 1:83 with boxes lc rgb "purple" notitle;
set title "R#0067";
plot data using 1:84 with boxes lc rgb "cyan" notitle;
set title "R#0068";
plot data using 1:85 with boxes lc rgb "cyan" notitle;
set title "R#0069";
plot data using 1:86 with boxes lc rgb "cyan" notitle;
set title "R#0070";
plot data using 1:87 with boxes lc rgb "purple" notitle;
set title "R#0071";
plot data using 1:88 with boxes lc rgb "magenta" notitle;
set title "R#0072";
plot data using 1:89 with boxes lc rgb "green" notitle;
set title "R#0073";
plot data using 1:90 with boxes lc rgb "khaki" notitle;
set title "R#0074";
plot data using 1:91 with boxes lc rgb "brown" notitle;
set title "R#0075";
plot data using 1:92 with boxes lc rgb "purple" notitle;
set title "R#0076";
plot data using 1:93 with boxes lc rgb "orange" notitle;
set title "R#0077";
plot data using 1:94 with boxes lc rgb "purple" notitle;
set title "R#0078";
plot data using 1:95 with boxes lc rgb "purple" notitle;
set title "R#0079";
plot data using 1:96 with boxes lc rgb "orange" notitle;
set title "R#0080";
plot data using 1:97 with boxes lc rgb "cyan" notitle;
set title "R#0081";
plot data using 1:98 with boxes lc rgb "khaki" notitle;
set title "R#0082";
plot data using 1:99 with boxes lc rgb "orange" notitle;
set title "R#0083";
plot data using 1:100 with boxes lc rgb "orange" notitle;
set title "R#0084";
plot data using 1:101 with boxes lc rgb "purple" notitle;
set title "R#0085";
plot data using 1:102 with boxes lc rgb "brown" notitle;
set title "R#0086";
plot data using 1:103 with boxes lc rgb "orange" notitle;
set title "R#0087";
plot data using 1:104 with boxes lc rgb "orange" notitle;
set title "R#0088";
plot data using 1:105 with boxes lc rgb "orange" notitle;
set title "R#0089";
plot data using 1:106 with boxes lc rgb "green" notitle;
set title "R#0090";
plot data using 1:107 with boxes lc rgb "magenta" notitle;
set title "R#0091";
plot data using 1:108 with boxes lc rgb "cyan" notitle;
set title "R#0092";
plot data using 1:109 with boxes lc rgb "khaki" notitle;
set title "R#0093";
plot data using 1:110 with boxes lc rgb "cyan" notitle;
set title "R#0094";
plot data using 1:111 with boxes lc rgb "purple" notitle;
set title "R#0095";
plot data using 1:112 with boxes lc rgb "khaki" notitle;
set title "R#0096";
plot data using 1:113 with boxes lc rgb "green" notitle;
set title "R#0097";
plot data using 1:114 with boxes lc rgb "orange" notitle;
set title "R#0098";
plot data using 1:115 with boxes lc rgb "purple" notitle;
set title "R#0099";
plot data using 1:116 with boxes lc rgb "navy" notitle;
set title "R#0100";
plot data using 1:117 with boxes lc rgb "cyan" notitle;
set title "R#0101";
plot data using 1:118 with boxes lc rgb "navy" notitle;
set title "R#0102";
plot data using 1:119 with boxes lc rgb "orange" notitle;
set title "R#0103";
plot data using 1:120 with boxes lc rgb "brown" notitle;
set title "R#0104";
plot data using 1:121 with boxes lc rgb "blue" notitle;
set title "R#0105";
plot data using 1:122 with boxes lc rgb "cyan" notitle;
set title "R#0106";
plot data using 1:123 with boxes lc rgb "blue" notitle;
set title "R#0107";
plot data using 1:124 with boxes lc rgb "orange" notitle;
set title "R#0108";
plot data using 1:125 with boxes lc rgb "blue" notitle;
set title "R#0109";
plot data using 1:126 with boxes lc rgb "blue" notitle;
set title "R#0110";
plot data using 1:127 with boxes lc rgb "blue" notitle;
set title "R#0111";
plot data using 1:128 with boxes lc rgb "blue" notitle;
set title "R#0112";
plot data using 1:129 with boxes lc rgb "orange" notitle;
set title "R#0113";
plot data using 1:130 with boxes lc rgb "navy" notitle;
set title "R#0114";
plot data using 1:131 with boxes lc rgb "magenta" notitle;
set title "R#0115";
plot data using 1:132 with boxes lc rgb "navy" notitle;
set title "R#0116";
plot data using 1:133 with boxes lc rgb "purple" notitle;
set title "R#0117";
plot data using 1:134 with boxes lc rgb "navy" notitle;
set title "R#0118";
plot data using 1:135 with boxes lc rgb "khaki" notitle;
set title "R#0119";
plot data using 1:136 with boxes lc rgb "purple" notitle;
set title "R#0120";
plot data using 1:137 with boxes lc rgb "khaki" notitle;
set title "R#0121";
plot data using 1:138 with boxes lc rgb "cyan" notitle;
set title "R#0122";
plot data using 1:139 with boxes lc rgb "purple" notitle;
set title "R#0123";
plot data using 1:140 with boxes lc rgb "blue" notitle;
set title "R#0124";
plot data using 1:141 with boxes lc rgb "navy" notitle;
set title "R#0125";
plot data using 1:142 with boxes lc rgb "khaki" notitle;
set title "R#0126";
plot data using 1:143 with boxes lc rgb "navy" notitle;
set title "R#0127";
plot data using 1:144 with boxes lc rgb "navy" notitle;
set title "R#0128";
plot data using 1:145 with boxes lc rgb "blue" notitle;
set title "R#0129";
plot data using 1:146 with boxes lc rgb "magenta" notitle;
set title "R#0130";
plot data using 1:147 with boxes lc rgb "orange" notitle;
set title "R#0131";
plot data using 1:148 with boxes lc rgb "magenta" notitle;
set title "R#0132";
plot data using 1:149 with boxes lc rgb "navy" notitle;
set title "R#0133";
plot data using 1:150 with boxes lc rgb "purple" notitle;
set title "R#0134";
plot data using 1:151 with boxes lc rgb "cyan" notitle;
set title "R#0135";
plot data using 1:152 with boxes lc rgb "purple" notitle;
set title "R#0136";
plot data using 1:153 with boxes lc rgb "brown" notitle;
set title "R#0137";
plot data using 1:154 with boxes lc rgb "blue" notitle;
set title "R#0138";
plot data using 1:155 with boxes lc rgb "magenta" notitle;
set title "R#0139";
plot data using 1:156 with boxes lc rgb "navy" notitle;
set title "R#0140";
plot data using 1:157 with boxes lc rgb "magenta" notitle;
set title "R#0141";
plot data using 1:158 with boxes lc rgb "purple" notitle;
set title "R#0142";
plot data using 1:159 with boxes lc rgb "blue" notitle;
set title "R#0143";
plot data using 1:160 with boxes lc rgb "green" notitle;
set title "R#0144";
plot data using 1:161 with boxes lc rgb "blue" notitle;
set title "R#0145";
plot data using 1:162 with boxes lc rgb "orange" notitle;
set title "R#0146";
plot data using 1:163 with boxes lc rgb "magenta" notitle;
set title "R#0147";
plot data using 1:164 with boxes lc rgb "orange" notitle;
set title "R#0148";
plot data using 1:165 with boxes lc rgb "brown" notitle;
set title "R#0149";
plot data using 1:166 with boxes lc rgb "magenta" notitle;
set title "R#0150";
plot data using 1:167 with boxes lc rgb "orange" notitle;
set title "R#0151";
plot data using 1:168 with boxes lc rgb "magenta" notitle;
set title "R#0152";
plot data using 1:169 with boxes lc rgb "green" notitle;
set title "R#0153";
plot data using 1:170 with boxes lc rgb "blue" notitle;
set title "R#0154";
plot data using 1:171 with boxes lc rgb "navy" notitle;
set title "R#0155";
plot data using 1:172 with boxes lc rgb "green" notitle;
set title "R#0156";
plot data using 1:173 with boxes lc rgb "navy" notitle;
set title "R#0157";
plot data using 1:174 with boxes lc rgb "green" notitle;
set title "R#0158";
plot data using 1:175 with boxes lc rgb "magenta" notitle;
set title "R#0159";
plot data using 1:176 with boxes lc rgb "blue" notitle;
set title "R#0160";
plot data using 1:177 with boxes lc rgb "brown" notitle;
set title "R#0161";
plot data using 1:178 with boxes lc rgb "blue" notitle;
set title "R#0162";
plot data using 1:179 with boxes lc rgb "blue" notitle;
set title "R#0163";
plot data using 1:180 with boxes lc rgb "magenta" notitle;
set title "R#0164";
plot data using 1:181 with boxes lc rgb "purple" notitle;
set title "R#0165";
plot data using 1:182 with boxes lc rgb "cyan" notitle;
set title "R#0166";
plot data using 1:183 with boxes lc rgb "orange" notitle;
set title "R#0167";
plot data using 1:184 with boxes lc rgb "orange" notitle;
set title "R#0168";
plot data using 1:185 with boxes lc rgb "navy" notitle;
set title "R#0169";
plot data using 1:186 with boxes lc rgb "purple" notitle;
set title "R#0170";
plot data using 1:187 with boxes lc rgb "navy" notitle;
set title "R#0171";
plot data using 1:188 with boxes lc rgb "cyan" notitle;
set title "R#0172";
plot data using 1:189 with boxes lc rgb "cyan" notitle;
set title "R#0173";
plot data using 1:190 with boxes lc rgb "blue" notitle;
set title "R#0174";
plot data using 1:191 with boxes lc rgb "blue" notitle;
set title "R#0175";
plot data using 1:192 with boxes lc rgb "cyan" notitle;
set title "R#0176";
plot data using 1:193 with boxes lc rgb "blue" notitle;
set title "R#0177";
plot data using 1:194 with boxes lc rgb "cyan" notitle;
set title "R#0178";
plot data using 1:195 with boxes lc rgb "navy" notitle;
set title "R#0179";
plot data using 1:196 with boxes lc rgb "cyan" notitle;
set title "R#0180";
plot data using 1:197 with boxes lc rgb "purple" notitle;
set title "R#0181";
plot data using 1:198 with boxes lc rgb "khaki" notitle;
set title "R#0182";
plot data using 1:199 with boxes lc rgb "magenta" notitle;
set title "R#0183";
plot data using 1:200 with boxes lc rgb "green" notitle;
set title "R#0184";
plot data using 1:201 with boxes lc rgb "orange" notitle;
set title "R#0185";
plot data using 1:202 with boxes lc rgb "blue" notitle;
set title "R#0186";
plot data using 1:203 with boxes lc rgb "navy" notitle;
set title "R#0187";
plot data using 1:204 with boxes lc rgb "purple" notitle;
set title "R#0188";
plot data using 1:205 with boxes lc rgb "brown" notitle;
set title "R#0189";
plot data using 1:206 with boxes lc rgb "navy" notitle;
set title "R#0190";
plot data using 1:207 with boxes lc rgb "green" notitle;
set title "R#0191";
plot data using 1:208 with boxes lc rgb "cyan" notitle;
set title "R#0192";
plot data using 1:209 with boxes lc rgb "navy" notitle;
set title "R#0193";
plot data using 1:210 with boxes lc rgb "blue" notitle;
set title "R#0194";
plot data using 1:211 with boxes lc rgb "khaki" notitle;
set title "R#0195";
plot data using 1:212 with boxes lc rgb "brown" notitle;
set title "R#0196";
plot data using 1:213 with boxes lc rgb "khaki" notitle;
set title "R#0197";
plot data using 1:214 with boxes lc rgb "orange" notitle;
set title "R#0198";
plot data using 1:215 with boxes lc rgb "purple" notitle;
set title "R#0199";
plot data using 1:216 with boxes lc rgb "blue" notitle;
set title "R#0200";
plot data using 1:217 with boxes lc rgb "navy" notitle;
set title "R#0201";
plot data using 1:218 with boxes lc rgb "purple" notitle;
set title "R#0202";
plot data using 1:219 with boxes lc rgb "blue" notitle;
set title "R#0203";
plot data using 1:220 with boxes lc rgb "khaki" notitle;
set title "R#0204";
plot data using 1:221 with boxes lc rgb "magenta" notitle;
set title "R#0205";
plot data using 1:222 with boxes lc rgb "orange" notitle;
set title "R#0206";
plot data using 1:223 with boxes lc rgb "purple" notitle;
set title "R#0207";
plot data using 1:224 with boxes lc rgb "brown" notitle;
set title "R#0208";
plot data using 1:225 with boxes lc rgb "green" notitle;
set title "R#0209";
plot data using 1:226 with boxes lc rgb "brown" notitle;
set title "R#0210";
plot data using 1:227 with boxes lc rgb "navy" notitle;
set title "R#0211";
plot data using 1:228 with boxes lc rgb "orange" notitle;
set title "R#0212";
plot data using 1:229 with boxes lc rgb "green" notitle;
set title "R#0213";
plot data using 1:230 with boxes lc rgb "khaki" notitle;
set title "R#0214";
plot data using 1:231 with boxes lc rgb "green" notitle;
set title "R#0215";
plot data using 1:232 with boxes lc rgb "blue" notitle;
set title "R#0216";
plot data using 1:233 with boxes lc rgb "blue" notitle;
set title "R#0217";
plot data using 1:234 with boxes lc rgb "navy" notitle;
set title "R#0218";
plot data using 1:235 with boxes lc rgb "navy" notitle;
set title "R#0219";
plot data using 1:236 with boxes lc rgb "green" notitle;
set title "R#0220";
plot data using 1:237 with boxes lc rgb "navy" notitle;
set title "R#0221";
plot data using 1:238 with boxes lc rgb "blue" notitle;
set title "R#0222";
plot data using 1:239 with boxes lc rgb "blue" notitle;
set title "R#0223";
plot data using 1:240 with boxes lc rgb "green" notitle;
set title "R#0224";
plot data using 1:241 with boxes lc rgb "green" notitle;
set title "R#0225";
plot data using 1:242 with boxes lc rgb "green" notitle;
set title "R#0226";
plot data using 1:243 with boxes lc rgb "green" notitle;
set title "R#0227";
plot data using 1:244 with boxes lc rgb "blue" notitle;
set title "R#0228";
plot data using 1:245 with boxes lc rgb "blue" notitle;
set title "R#0229";
plot data using 1:246 with boxes lc rgb "blue" notitle;
set title "R#0230";
plot data using 1:247 with boxes lc rgb "blue" notitle;
set title "R#0231";
plot data using 1:248 with boxes lc rgb "navy" notitle;
set title "R#0232";
plot data using 1:249 with boxes lc rgb "navy" notitle;
set title "R#0233";
plot data using 1:250 with boxes lc rgb "navy" notitle;
set title "R#0234";
plot data using 1:251 with boxes lc rgb "navy" notitle;
set title "R#0235";
plot data using 1:252 with boxes lc rgb "navy" notitle;
set title "R#0236";
plot data using 1:253 with boxes lc rgb "navy" notitle;
set title "R#0237";
plot data using 1:254 with boxes lc rgb "navy" notitle;
set title "R#0238";
plot data using 1:255 with boxes lc rgb "purple" notitle;
set title "R#0239";
plot data using 1:256 with boxes lc rgb "purple" notitle;
set title "R#0240";
plot data using 1:257 with boxes lc rgb "purple" notitle;
set title "R#0241";
plot data using 1:258 with boxes lc rgb "purple" notitle;
set title "R#0242";
plot data using 1:259 with boxes lc rgb "green" notitle;
set title "R#0243";
plot data using 1:260 with boxes lc rgb "green" notitle;
set title "R#0244";
plot data using 1:261 with boxes lc rgb "green" notitle;
set title "R#0245";
plot data using 1:262 with boxes lc rgb "green" notitle;
set title "R#0246";
plot data using 1:263 with boxes lc rgb "green" notitle;
set title "R#0247";
plot data using 1:264 with boxes lc rgb "green" notitle;
set title "R#0248";
plot data using 1:265 with boxes lc rgb "green" notitle;
unset multiplot;
