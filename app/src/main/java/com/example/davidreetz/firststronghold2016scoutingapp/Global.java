package com.example.davidreetz.firststronghold2016scoutingapp;

import java.io.File;

/**
 * Created by David Reetz on 12/17/2015.
 */
public class Global {
    public static final int
            ioTeam = 0,
            ioMatch = ioTeam + 1,
            ioRobot = ioMatch + 1,
            ioName = ioRobot + 1,
            ioR2Obs = ioName + 1,
            ioR3Obs = ioR2Obs + 1,
            ioR4Obs = ioR3Obs + 1,
            ioR5Obs = ioR4Obs + 1,
            ioB2Obs = ioR5Obs + 1,
            ioB3Obs = ioB2Obs + 1,
            ioB4Obs = ioB3Obs + 1,
            ioB5Obs = ioB4Obs + 1,
            ioStartPos = ioB5Obs + 1,
            ioStartBall = ioStartPos + 1,
            ioAutoHigh = ioStartBall + 1,
            ioAutoMissHigh = ioAutoHigh + 1,
            ioAutoLow = ioAutoMissHigh + 1,
            ioAutoMissLow = ioAutoLow + 1,
            ioAutoCarry = ioAutoMissLow + 1,
            ioAutoStuck = ioAutoCarry + 1,
            ioAutoCross = ioAutoStuck + 1,
            ioAutoReach = ioAutoCross + 1,
            ioHigh = ioAutoReach + 1,
            ioMissHigh = ioHigh + 1,
            ioLow = ioMissHigh + 1,
            ioMissLow = ioLow + 1,
            ioNumCrossesLoBar = ioMissLow + 1,
            ioTimeLoBar = ioNumCrossesLoBar + 1,
            ioNumCrossesShovel = ioTimeLoBar + 1,
            ioTimeShovel = ioNumCrossesShovel + 1,
            ioNumCrossesPortcullis = ioTimeShovel + 1,
            ioTimePortcullis = ioNumCrossesPortcullis + 1,
            ioNumCrossesMoat = ioTimePortcullis + 1,
            ioTimeMoat = ioNumCrossesMoat + 1,
            ioNumCrossesRamparts = ioTimeMoat + 1,
            ioTimeRamparts = ioNumCrossesRamparts + 1,
            ioNumCrossesSallyPort = ioTimeRamparts + 1,
            ioTimeSallyPort = ioNumCrossesSallyPort + 1,
            ioNumCrossesDrawbridge = ioTimeSallyPort + 1,
            ioTimeDrawbridge = ioNumCrossesDrawbridge + 1,
            ioNumCrossesRockWall = ioTimeDrawbridge + 1,
            ioTimeRockWall = ioNumCrossesRockWall + 1,
            ioNumCrossesRoughTerrain = ioTimeRockWall + 1,
            ioTimeRoughTerrain = ioNumCrossesRoughTerrain + 1,
            ioStuck = ioTimeRoughTerrain + 1,
            ioCarry = ioStuck + 1,
            ioChallenge = ioCarry + 1,
            ioScale = ioChallenge + 1,
            ioTechFouls = ioScale + 1,
            ioDieAuto = ioTechFouls + 1,
            ioDieTele = ioDieAuto + 1,
            ioDeadAll = ioDieTele + 1,
            ioIntermittet = ioDeadAll + 1,
            ioTipped = ioIntermittet + 1,
            ioDefenseInterfere = ioTipped + 1,
            ioDefenseBlock = ioDefenseInterfere + 1,
            ioComments = ioDefenseBlock + 1,
            ioFailLoBar = ioComments + 1,
            ioFailShovel = ioFailLoBar + 1,
            ioFailPortcullis = ioFailShovel + 1,
            ioFailMoat = ioFailPortcullis + 1,
            ioFailRamparts = ioFailMoat + 1,
            ioFailSallyPort = ioFailRamparts + 1,
            ioFailDrawbridge = ioFailSallyPort + 1,
            ioFailRockWall = ioFailDrawbridge + 1,
            ioFailRoughTerrain = ioFailRockWall + 1,
            ioWandering = ioFailRoughTerrain + 1,
            ioDefending = ioWandering + 1,
            ioRolledOff = ioDefending + 1,
            ioBallStuck = ioRolledOff + 1,
            ioCapture = ioBallStuck + 1,
            ioBreach = ioCapture + 1,
            ioDefended = ioBreach + 1,
            ioROB = ioCapture + 1;
    static String startLoc, Robot, User;
    static String[] stats = new String[74];
    boolean Stuckified = false;
    String ObstaclesStuck[];
    Boolean DeadAuto, DeadTele;
    int firstMatch;
    int matchesSinceCollect = 0;
    int MatchNum = 1;
    static Global data;
    File OldFile = new File("");

    public int getMatch() {
        return Integer.parseInt(stats[ioMatch]);
    }

    public void setMatch(int match) {
        stats[ioMatch] = "" + match;
    }

    public String getRobot() {
        return stats[ioRobot];
    }

    public void setRobot(String robot) {
        stats[ioRobot] = robot;
    }

    public String getName() {
        return stats[ioName];
    }

    public void setName(String name) {
        stats[ioName] = name;
    }

    public int getTeam() {
        return Integer.parseInt(stats[ioTeam]);
    }

    public void setTeam(String team) {
        stats[ioTeam] = team;
    }

    public String getR2Obs() {
        if (stats[ioR2Obs] == null) {
            return "";
        } else {
            return stats[ioR2Obs];
        }
    }

    public void setR2Obs(String R2Obs) {
        stats[ioR2Obs] = R2Obs;
    }


    public String getR3Obs() {
        if (stats[ioR3Obs] == null) {
            return "";
        } else {
            return stats[ioR3Obs];
        }
    }

    public void setR3Obs(String R3Obs) {
        stats[ioR3Obs] = R3Obs;
    }

    public String getR4Obs() {
        if (stats[ioR4Obs] == null) {
            return "";
        } else {
            return stats[ioR4Obs];
        }
    }

    public void setR4Obs(String R4Obs) {
        stats[ioR4Obs] = R4Obs;
    }

    public String getR5Obs() {
        if (stats[ioR5Obs] == null) {
            return "";
        } else {
            return stats[ioR5Obs];
        }
    }

    public void setR5Obs(String R5Obs) {
        stats[ioR5Obs] = R5Obs;
    }

    public String getB2Obs() {
        if (stats[ioB2Obs] == null) {
            return "";
        } else {
            return stats[ioB2Obs];
        }
    }

    public void setB2Obs(String B2Obs) {
        stats[ioB2Obs] = B2Obs;
    }

    public String getB3Obs() {
        if (stats[ioB3Obs] == null) {
            return "";
        } else {
            return stats[ioB3Obs];
        }
    }

    public void setB3Obs(String B3Obs) {
        stats[ioB3Obs] = B3Obs;
    }

    public String getB4Obs() {
        if (stats[ioB4Obs] == null) {
            return "";
        } else {
            return stats[ioB4Obs];
        }
    }

    public void setB4Obs(String B4Obs) {
        stats[ioB4Obs] = B4Obs;
    }

    public String getB5Obs() {
        if (stats[ioB5Obs] == null) {
            return "";
        } else {
            return stats[ioB5Obs];
        }
    }

    public void setB5Obs(String B5Obs) {
        stats[ioB5Obs] = B5Obs;
    }

    public String getStartPos() {
        return stats[ioStartPos];
    }

    public void setStartPos(String StartPos) {
        stats[ioStartPos] = StartPos;
    }

    public boolean getStartBall() {
        if (stats[ioB2Obs] == null) {
            return false;
        } else {
            return Boolean.parseBoolean(stats[ioStartBall]);
        }
    }

    public void setStartBall(boolean StartBall) {
        stats[ioStartBall] = "" + StartBall;
    }

    public int getAutoReach() {
        if (stats[ioAutoReach] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioAutoReach]);
        }
    }

    public void setAutoReach(int AutoReach) {
        stats[ioAutoReach] = "" + AutoReach;
    }

    public boolean getAutoHigh() {
        if (stats[ioAutoHigh] == null) {
            return false;
        } else {
            return Boolean.parseBoolean(stats[ioAutoHigh]);
        }
    }

    public void setAutoHigh(boolean AutoHigh) {
        stats[ioAutoHigh] = "" + AutoHigh;
    }

    public boolean getAutoMissHigh() {
        if (stats[ioAutoMissHigh] == null) {
            return false;
        } else {
            return Boolean.parseBoolean(stats[ioAutoMissHigh]);
        }
    }

    public void setAutoMissHigh(boolean AutoMissHigh) {
        stats[ioAutoMissHigh] = "" + AutoMissHigh;
    }

    public boolean getAutoLow() {
        if (stats[ioAutoLow] == null) {
            return false;
        } else {
            return Boolean.parseBoolean(stats[ioAutoLow]);
        }
    }

    public void setAutoLow(boolean AutoLow) {
        stats[ioAutoLow] = "" + AutoLow;
    }

    public boolean getAutoMissLow() {
        if (stats[ioAutoMissLow] == null) {
            return false;
        } else {
            return Boolean.parseBoolean(stats[ioAutoMissLow]);
        }
    }

    public void setAutoMissLow(boolean AutoMissLow) {
        stats[ioAutoMissLow] = "" + AutoMissLow;
    }

    public boolean getAutoCarry() {
        if (stats[ioAutoCarry] == null) {
            return false;
        } else {
            return Boolean.parseBoolean(stats[ioAutoCarry]);
        }
    }

    public void setAutoCarry(boolean AutoCarry) {
        stats[ioAutoCarry] = "" + AutoCarry;
    }

    public String getAutoStuck() {
        return stats[ioAutoStuck];
    }

    public void setAutoStuck(String AutoStuck) {
        stats[ioAutoStuck] = "" + AutoStuck;
    }

    public int getAutoCross() {
        return Integer.parseInt(stats[ioAutoCross]);
    }

    public void setAutoCross(int AutoCross) {
        stats[ioAutoCross] = "" + AutoCross;
    }

    public int getHigh() {
        if (stats[ioAutoHigh] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioAutoHigh]);
        }
    }

    public void setHigh(int high) {
        stats[ioHigh] = "" + high;
    }

    public int getMissHigh() {
        if (stats[ioMissHigh] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioMissHigh]);
        }
    }

    public void setMissHigh(int MissHigh) {
        stats[ioMissHigh] = "" + MissHigh;
    }

    public int getLow() {
        if (stats[ioLow] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioLow]);
        }
    }

    public void setLow(int Low) {
        stats[ioLow] = "" + Low;
    }

    public int getMissLow() {
        if (stats[ioMissLow] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioMissLow]);
        }
    }

    public void setMissLow(int MissLow) {
        stats[ioMissLow] = "" + MissLow;
    }

    public int getNumCrossesLoBar() {
        if (stats[ioNumCrossesLoBar] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioNumCrossesLoBar]);
        }
    }

    public void setNumCrossesLoBar(int NumCrossesLoBar) {
        stats[ioNumCrossesLoBar] = "" + NumCrossesLoBar;
    }

    public double getTimeLoBar() {
        if (stats[ioTimeLoBar] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioTimeLoBar]);
        }
    }

    public void setTimeLoBar(double TimeLoBar) {
        stats[ioTimeLoBar] = "" + TimeLoBar;
    }

    public int getNumCrossesShovel() {
        if (stats[ioNumCrossesShovel] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioNumCrossesShovel]);
        }
    }

    public void setNumCrossesShovel(int NumCrossesShovel) {
        stats[ioNumCrossesShovel] = "" + NumCrossesShovel;
    }

    public double getTimeShovel() {
        if (stats[ioTimeShovel] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioTimeShovel]);
        }
    }

    public void setTimeShovel(double TimeShovel) {
        stats[ioTimeShovel] = "" + TimeShovel;
    }

    public double getTimePortcullis() {
        if (stats[ioTimePortcullis] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioTimePortcullis]);
        }
    }

    public void setTimePortcullis(double TimePortcullis) {
        stats[ioTimePortcullis] = "" + TimePortcullis;
    }

    public int getNumCrossesPortcullis() {
        if (stats[ioNumCrossesPortcullis] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioNumCrossesPortcullis]);
        }
    }

    public void setNumCrossesPortcullis(int NumCrossesPortcullis) {
        stats[ioNumCrossesPortcullis] = "" + NumCrossesPortcullis;
    }


    public int getNumCrossesMoat() {
        if (stats[ioNumCrossesMoat] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioNumCrossesMoat]);
        }
    }

    public void setNumCrossesMoat(int NumCrossesMoat) {
        stats[ioNumCrossesMoat] = "" + NumCrossesMoat;
    }

    public double getTimeMoat() {
        if (stats[ioTimeMoat] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioTimeMoat]);
        }
    }

    public void setTimeMoat(double timeMoat) {
        stats[ioTimeMoat] = "" + timeMoat;
    }

    public int getIoNumCrossesRamparts() {
        if (stats[ioNumCrossesRamparts] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioNumCrossesRamparts]);
        }
    }

    public void setNumCrossesRamparts(int NumCrossesRamparts) {
        stats[ioNumCrossesRamparts] = "" + NumCrossesRamparts;

    }

    public double getTimeRamparts() {
        if (stats[ioTimeRamparts] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioTimeRamparts]);
        }
    }

    public void setTimeRamparts(double TimeRamparts) {
        stats[ioTimeRamparts] = "" + TimeRamparts;
    }

    public int getNumCrossesSallyPort() {
        if (stats[ioNumCrossesSallyPort] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioNumCrossesSallyPort]);
        }
    }

    public void setNumCrossesSallyPort(int NumCrossesSallyPort) {
        stats[ioNumCrossesSallyPort] = "" + NumCrossesSallyPort;
    }

    public double getTimeSallyPort() {
        if (stats[ioTimeSallyPort] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioTimeSallyPort]);
        }
    }

    public void setTimeSallyPort(double TimeSallyPort) {
        stats[ioTimeSallyPort] = "" + TimeSallyPort;
    }

    public int getNumCrossesDrawbridge() {
        if (stats[ioNumCrossesDrawbridge] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioNumCrossesDrawbridge]);
        }
    }

    public void setNumCrossesDrawbridge(int NumCrossesDrawbridge) {
        stats[ioNumCrossesDrawbridge] = "" + NumCrossesDrawbridge;

    }

    public double getTimeDrawbridge() {
        if (stats[ioTimeDrawbridge] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioTimeDrawbridge]);
        }
    }

    public void setTimeDrawbridge(double TimeDrawbridge) {
        stats[ioTimeDrawbridge] = "" + TimeDrawbridge;
    }

    public int getNumCrossesRoughTerrain() {
        if (stats[ioNumCrossesRoughTerrain] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioNumCrossesRoughTerrain]);
        }
    }

    public void setNumCrossesRoughTerrain(int NumCrossesRoughTerrain) {
        stats[ioNumCrossesRoughTerrain] = "" + NumCrossesRoughTerrain;
    }

    public double getTimeRoughTerrain() {
        if (stats[ioTimeRoughTerrain] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioTimeRoughTerrain]);
        }
    }

    public void setTimeRoughTerrain(double TimeRoughTerrain) {
        stats[ioTimeRoughTerrain] = "" + TimeRoughTerrain;

    }

    public int getNumCrossesRockWall() {

        if (stats[ioNumCrossesRockWall] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioNumCrossesRockWall]);
        }
    }

    public void setNumCrossesRockWall(int NumCrossesRockWall) {
        stats[ioNumCrossesRockWall] = "" + NumCrossesRockWall;
    }

    public double getTimeRockWall() {
        if (stats[ioTimeRockWall] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioTimeRockWall]);
        }
    }

    public void setTimeRockWall(double TimeRockWall) {
        stats[ioTimeRockWall] = "" + TimeRockWall;
    }

    public String getStuck() {
        return stats[ioStuck];
    }

    public void setStuck(String Stuck) {
        stats[ioStuck] = Stuck;
    }

    public int getCarry() {
        if (stats[ioCarry] == null) {
            return 0;
        } else {
            return Integer.parseInt(stats[ioCarry]);
        }
    }

    public void setCarry(int Carry) {

        stats[ioCarry] = "" + Carry;
    }

    public boolean getChallenge() {
        return Boolean.parseBoolean(stats[ioChallenge]);
    }

    public void setChallenge(boolean Challenge) {
        stats[ioChallenge] = "" + Challenge;
    }

    public boolean getScale() {
        return Boolean.parseBoolean(stats[ioScale]);
    }

    public void setScale(boolean Scale) {
        stats[ioScale] = "" + Scale;
    }

    public int getTechFouls() {
        return Integer.parseInt(stats[ioTechFouls]);
    }

    public void setTechFouls(int TechFouls) {
        stats[ioTechFouls] = "" + TechFouls;
    }

    public boolean getDieAuto() {
        return Boolean.parseBoolean(stats[ioDieAuto]);
    }

    public void setDieAuto(boolean DieAuto) {
        stats[ioDieAuto] = "" + DieAuto;
    }

    public boolean getDieTele() {
        return Boolean.parseBoolean(stats[ioDieTele]);
    }

    public void setDieTele(boolean DieTele) {
        stats[ioDieTele] = "" + DieTele;
    }

    public boolean getIntermittet() {
        return Boolean.parseBoolean(stats[ioIntermittet]);
    }

    public void setIntermittet(boolean Intermittet) {
        stats[ioIntermittet] = "" + Intermittet;
    }

    public boolean getTipped() {
        return Boolean.parseBoolean(stats[ioTipped]);
    }

    public void setTipped(boolean Tipped) {
        stats[ioTipped] = "" + Tipped;
    }

    public int getDefenseInterfere() {
        return Integer.parseInt(stats[ioDefenseInterfere]);
    }

    public void setDefenseInterfere(int DefenseInterfere) {
        stats[ioDefenseInterfere] = "" + DefenseInterfere;
    }

    public int getDefenseBlock() {
        return Integer.parseInt(stats[ioDefenseBlock]);
    }

    public void setDefenseBlock(int DefenseBlock) {
        stats[ioDefenseBlock] = "" + DefenseBlock;
    }

    public String getComments() {
        return stats[ioComments];
    }

    public void setComments(String Comments) {
        stats[ioComments] = Comments;
    }

    public int getFailLoBar() {
        return Integer.parseInt(stats[ioFailLoBar]);
    }

    public void setFailLoBar(int FailLowBar) {
        stats[ioFailLoBar] = "" + FailLowBar;
    }

    public int getFailShovel() {
        return Integer.parseInt(stats[ioFailShovel]);
    }

    public void setFailShovel(int FailShovel) {
        stats[ioFailShovel] = "" + FailShovel;
    }

    public int getFailPortcullis() {
        return Integer.parseInt(stats[ioFailPortcullis]);
    }

    public void setFailPortcullis(int FailPortcullis) {
        stats[ioFailPortcullis] = "" + FailPortcullis;
    }

    public int getFailMoat() {
        return Integer.parseInt(stats[ioFailMoat]);
    }

    public void setFailMoat(int FailMoat) {
        stats[ioFailMoat] = "" + FailMoat;
    }

    public int getFailRamparts() {
        return Integer.parseInt(stats[ioFailRamparts]);
    }

    public void setFailRamparts(int FailRamparts) {
        stats[ioFailRamparts] = "" + FailRamparts;
    }

    public int getFailSallyPort() {
        return Integer.parseInt(stats[ioFailSallyPort]);
    }

    public void setFailSallyPort(int FailSallyPort) {
        stats[ioFailSallyPort] = "" + FailSallyPort;
    }

    public int getFailDrawbridge() {
        return Integer.parseInt(stats[ioFailDrawbridge]);
    }

    public void setFailDrawbridge(int FailDrawbridge) {
        stats[ioFailDrawbridge] = "" + FailDrawbridge;
    }

    public int getFailRockWall() {
        return Integer.parseInt(stats[ioFailRockWall]);
    }

    public void setFailRockWall(int FailRockWall) {
        stats[ioFailRockWall] = "" + FailRockWall;
    }

    public int getFailRoughTerrain() {
        return Integer.parseInt(stats[ioFailRoughTerrain]);
    }

    public void setFailRoughTerrain(int FailRoughTerrain) {
        stats[ioFailRoughTerrain] = "" + FailRoughTerrain;
    }

    public long getDefending() {
        return Long.parseLong(stats[ioDefending]);
    }

    public void setDefending(long Defending) {
        stats[ioDefending] = "" + Defending;
    }

    public long getWandering() {
        return Long.parseLong(stats[ioWandering]);
    }

    public void setWandering(long Wandering) {
        stats[ioWandering] = "" + Wandering;
    }

    public void setRolledOff(boolean RolledOff) {
        stats[ioRolledOff] = "" + RolledOff;
    }

    public void setBallStuck(boolean BallStuck) {
        stats[ioBallStuck] = "" + BallStuck;
    }

    public void setCapture(boolean Capture) {
        stats[ioCapture] = "" + Capture;
    }

    public void setBreach(boolean Breach) {
        stats[ioBreach] = "" + Breach;
    }

    public void setDefended(boolean Defended) {
        stats[ioDefended] = "" + Defended;
    }

    public String getROB() {
        return stats[ioROB];
    }

    public void setROB(String ROB) {
        stats[ioROB] = "" + ROB;
    }

    public int getMatchNum() {
        return MatchNum;
    }

    public void setMatchNum(int MN) {
        MatchNum = MN;
    }


}
