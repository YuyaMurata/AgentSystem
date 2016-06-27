-- -----------------------------
-- create table of ranksys
-- -----------------------------

-- <!--UserAgent Entity define-->
-- <entity type="aggregateagent">
--      <attribute name="AgentID" type="string" primarykey="true" maxlength="32"/>
--      <attribute name="Condition" type="condition" singleentity="true"/>
--	<attribute name="Data" type="long" />
--	<attribute name="ConnectionCount" type="long" />
--	<attribute name="Log" type="log" />
--</entity>
--<entity type="condition">
--      <attribute name="AgentID" type="string" primarykey="true" relationto="UserID" maxlength="32"/>
--	<attribute name="AggregateCondition" type="string" maxlength="32"/>
--	<attribute name="LastAccessTime" type="timestamp" />
--</entity>
--<entity type="log">
--	<attribute name="AgentID" type="string" primarykey="true" relationto="UserID" maxlength="32"/>
--	<attribute name="AccessID" type="string" primarykey="true" maxlength="16"/>
--	<attribute name="CurrentTime" type="long"/>
--      <attribute name="LastAccessTime" type="timestamp" />
--</entity>

CREATE TRANSIENT TABLE aggregateagent (
	AgentID VARCHAR(64) NOT NULL,
	Data BIGINT,
	ConnectionCount BIGINT,
	PRIMARY KEY(AgentID)
);
COMMIT WORK;

CREATE TRANSIENT TABLE condition(
	AgentID VARCHAR(64) NOT NULL,
	AggregateCondition WVARCHAR(64),
	LastAccessTime TIMESTAMP,
	PRIMARY KEY(AgentID)
);
COMMIT WORK;

CREATE TRANSIENT TABLE log(
	AgentID VARCHAR(64) NOT NULL,
	AccessID VARCHAR(16) NOT NULL,
        CurrentTime BIGINT,
	LastAccessTime TIMESTAMP,
	PRIMARY KEY(AgentID, AccessID)
);
COMMIT WORK;

