
-- Database: `assetdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `cities`
--

CREATE TABLE `cities` (
  `cityId` int(10) NOT NULL,
  `city` varchar(20) NOT NULL,
  `countryId` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cities`
--

INSERT INTO `cities` (`cityId`, `city`, `countryId`) VALUES
(1, 'Mumbai', 1),
(2, 'Bangalore', 1),
(3, 'Sydney', 2),
(4, 'Perth', 2),
(5, 'Bonn', 3),
(6, 'Bamberg', 3);

-- --------------------------------------------------------

--
-- Table structure for table `countries`
--

CREATE TABLE `countries` (
  `countryId` int(10) NOT NULL,
  `country` varchar(20) NOT NULL,
  `capital` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `countries`
--

INSERT INTO `countries` (`countryId`, `country`, `capital`) VALUES
(1, 'India', 'NewDelhi'),
(2, 'Australia', 'Canberra'),
(3, 'Germany', 'Berlin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cities`
--
ALTER TABLE `cities`
  ADD PRIMARY KEY (`cityId`);

--
-- Indexes for table `countries`
--
ALTER TABLE `countries`
  ADD PRIMARY KEY (`countryId`);

