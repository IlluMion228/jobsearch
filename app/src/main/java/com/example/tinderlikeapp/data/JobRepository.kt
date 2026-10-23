package com.example.tinderlikeapp.data

import com.example.tinderlikeapp.model.Job
import com.example.tinderlikeapp.model.JobCategory

class JobRepository {

    fun getSampleJobs(): List<Job> {
        return listOf(
            Job(
                id = "job_1",
                title = "Barista & Cafe Assistant",
                company = "Roasted Bean Coffee Co.",
                category = JobCategory.HOSPITALITY,
                hourlyRate = 22.50,
                latitude = 40.7180,
                longitude = -74.0020,
                locationName = "Downtown Plaza",
                shiftInfo = "Morning Shift (07:00 - 14:30)",
                description = "Craft espresso drinks, handle register transactions, and maintain a welcoming atmosphere for daily regulars. Free meals & coffee included.",
                tags = listOf("Tips Included", "Free Meals", "Immediate Start"),
                employerMatchProbability = 0.80,
                primaryColorHex = 0xFF795548,
                postedAgo = "10 mins ago"
            ),
            Job(
                id = "job_2",
                title = "Order Picker & Packer",
                company = "LogiTech Logistics Hub",
                category = JobCategory.WAREHOUSE,
                hourlyRate = 25.00,
                latitude = 40.7420,
                longitude = -73.9850,
                locationName = "North Industrial District",
                shiftInfo = "Night Shift (22:00 - 06:00)",
                description = "Scan, pick, and package online orders using automated barcode tools. Night shift differential ($2/hr bonus included in rate).",
                tags = listOf("Night Bonus", "Weekly Pay", "No Exp Needed"),
                employerMatchProbability = 0.90,
                primaryColorHex = 0xFFFF9800,
                postedAgo = "1 hour ago"
            ),
            Job(
                id = "job_3",
                title = "Express Delivery Driver",
                company = "SwiftCourier Express",
                category = JobCategory.DELIVERY,
                hourlyRate = 27.80,
                latitude = 40.7250,
                longitude = -74.0150,
                locationName = "Central Depot",
                shiftInfo = "Flexible Shifts (Choice of 4h/8h)",
                description = "Deliver light parcels across city center routes using company van or your bike. Fuel stipend and phone allowance provided.",
                tags = listOf("Company Van", "Flexible Hours", "Fuel Stipend"),
                employerMatchProbability = 0.75,
                primaryColorHex = 0xFF00BCD4,
                postedAgo = "30 mins ago"
            ),
            Job(
                id = "job_4",
                title = "Retail Sales Floor Rep",
                company = "Urban Style Outfitters",
                category = JobCategory.RETAIL,
                hourlyRate = 21.00,
                latitude = 40.7100,
                longitude = -74.0080,
                locationName = "Metro Center Mall",
                shiftInfo = "Afternoon (13:00 - 21:00)",
                description = "Assist customers with sizing, restock apparel displays, and assist with store promotions. 40% employee store discount.",
                tags = listOf("40% Discount", "Weekend Shift", "Friendly Team"),
                employerMatchProbability = 0.60,
                primaryColorHex = 0xFFE91E63,
                postedAgo = "2 hours ago"
            ),
            Job(
                id = "job_5",
                title = "Commercial Office Cleaner",
                company = "SparkleClean Facilities",
                category = JobCategory.CLEANING,
                hourlyRate = 24.00,
                latitude = 40.7380,
                longitude = -74.0250,
                locationName = "Tech Park Tower B",
                shiftInfo = "Evening Shift (18:00 - 22:00)",
                description = "Perform routine cleaning and sanitization of high-end corporate office spaces. Perfect for part-time income.",
                tags = listOf("Part-time", "Quiet Work", "Equipment Provided"),
                employerMatchProbability = 0.85,
                primaryColorHex = 0xFF009688,
                postedAgo = "3 hours ago"
            ),
            Job(
                id = "job_6",
                title = "Event Bartender & Server",
                company = "Crown Catering & Events",
                category = JobCategory.HOSPITALITY,
                hourlyRate = 29.50,
                latitude = 40.7650,
                longitude = -73.9650,
                locationName = "Waterfront Convention Center",
                shiftInfo = "Weekend Evening (17:00 - 01:00)",
                description = "Serve drinks and appetizers at premium corporate galas and wedding receptions. Great tips pool.",
                tags = listOf("High Tips", "Weekend Only", "Dynamic Work"),
                employerMatchProbability = 0.50,
                primaryColorHex = 0xFF673AB7,
                postedAgo = "Yesterday"
            ),
            Job(
                id = "job_7",
                title = "Grocery Restocker & Associate",
                company = "FreshMarket Supermarket",
                category = JobCategory.RETAIL,
                hourlyRate = 19.50,
                latitude = 40.7850,
                longitude = -73.9450,
                locationName = "Eastside Shopping Strip",
                shiftInfo = "Early Morning (05:00 - 11:00)",
                description = "Receive morning produce deliveries, organize shelves, and rotate perishable items.",
                tags = listOf("Early Bird", "Steady Hours", "Health Insurance"),
                employerMatchProbability = 0.80,
                primaryColorHex = 0xFF4CAF50,
                postedAgo = "4 hours ago"
            ),
            Job(
                id = "job_8",
                title = "Forklift Operator (Licensed)",
                company = "Apex Freight Terminal",
                category = JobCategory.WAREHOUSE,
                hourlyRate = 31.00,
                latitude = 40.8250,
                longitude = -73.9100,
                locationName = "South Port Logistics",
                shiftInfo = "Full Time (08:00 - 16:30)",
                description = "Operate reach truck and counter-balance forklift in a fast-paced distribution facility. OSHA cert required.",
                tags = listOf("Certification Bonus", "Full Time", "Overtime Avail"),
                employerMatchProbability = 0.70,
                primaryColorHex = 0xFF3F51B5,
                postedAgo = "5 hours ago"
            ),
            Job(
                id = "job_9",
                title = "Hotel Front Desk Agent",
                company = "Grand Azure Hotel",
                category = JobCategory.HOSPITALITY,
                hourlyRate = 23.00,
                latitude = 40.8650,
                longitude = -73.8800,
                locationName = "Airport Business Corridor",
                shiftInfo = "Day Shift (08:00 - 16:00)",
                description = "Welcome guests, manage check-ins and check-outs, and answer phone inquiries. Hospitality experience preferred.",
                tags = listOf("Hotel Perks", "Paid Training", "Uniform Included"),
                employerMatchProbability = 0.65,
                primaryColorHex = 0xFF1E88E5,
                postedAgo = "6 hours ago"
            ),
            Job(
                id = "job_10",
                title = "E-Bike Delivery Courier",
                company = "QuickBite Food App",
                category = JobCategory.DELIVERY,
                hourlyRate = 26.00,
                latitude = 40.9150,
                longitude = -73.8300,
                locationName = "Westside Dining Quarter",
                shiftInfo = "Peak Lunch & Dinner Hours",
                description = "Pick up food orders from local restaurants and deliver on e-bikes. E-bike provided by company.",
                tags = listOf("Keep 100% Tips", "E-Bike Provided", "Flexible Schedule"),
                employerMatchProbability = 0.95,
                primaryColorHex = 0xFFF44336,
                postedAgo = "Just now"
            ),
            Job(
                id = "job_11",
                title = "Industrial Facilities Cleaner",
                company = "PureZone Sanitation",
                category = JobCategory.CLEANING,
                hourlyRate = 26.50,
                latitude = 40.9750,
                longitude = -73.7800,
                locationName = "Harbor Industrial Complex",
                shiftInfo = "Night Shift (20:00 - 04:00)",
                description = "Deep cleaning and pressure washing of industrial equipment and floor areas.",
                tags = listOf("High Pay Rate", "Safety Gear Free", "Overtime Pay"),
                employerMatchProbability = 0.70,
                primaryColorHex = 0xFF00ACC1,
                postedAgo = "Yesterday"
            )
        )
    }
}
