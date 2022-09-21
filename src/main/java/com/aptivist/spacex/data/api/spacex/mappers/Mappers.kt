package com.aptivist.spacex.data.api.spacex.mappers

import com.aptivist.spacex.data.api.spacex.models.*
import com.aptivist.spacex.domain.models.*

fun SpaceXListItemDTO.toDomain() : SpaceXListItem {

    val launch_failure_details = this.launch_failure_details?.toDomain()
    val launch_site = this.launch_site?.toDomain()
    val links = this.links?.toDomain()
    val rocket = this.rocket?.toDomain()
    val telemetry = this.telemetry?.toDomain()
    val timeline = this.timeline?.toDomain()

    return SpaceXListItem(
    crew = crew as List<String>?,
    details = details ?: null,
    flight_number = flight_number ?: null,
    is_tentative = is_tentative ?: null,
    last_date_update = last_date_update ?: null,
    last_ll_launch_date = last_ll_launch_date ?: null,
    last_ll_update = last_ll_update ?: null,
    last_wiki_launch_date = last_wiki_launch_date ?: null,
    last_wiki_revision = last_wiki_revision ?: null,
    last_wiki_update = last_wiki_update ?: null,
    launch_date_local = launch_date_local ?: null,
    launch_date_source = launch_date_source ?: null,
    launch_date_unix = launch_date_unix ?: null,
    launch_date_utc = launch_date_utc ?: null,
    launch_failure_details =  launch_failure_details,
    launch_site =  launch_site,
    launch_success = launch_success ?: null,
    launch_window = launch_window ?: null,
    launch_year = launch_year ?: null,
    links = links,
    mission_id = mission_id ?: null,
    mission_name = mission_name ?: null,
    rocket =  rocket,
    ships = ships ?: null,
    static_fire_date_unix = static_fire_date_unix ?: null,
    static_fire_date_utc = static_fire_date_utc ?: null,
    tbd = tbd ?: null,
    telemetry =  telemetry,
    tentative_max_precision = tentative_max_precision ?: null,
    upcoming = upcoming ?: null,
    timeline = timeline
    )
}

fun LaunchFailureDetailsDTO.toDomain() : LaunchFailureDetails = LaunchFailureDetails(
    altitude = altitude  ?: null,
    reason = reason ?: null,
    time = time
)

fun LaunchSiteDTO.toDomain() : LaunchSite = LaunchSite(
    site_id = site_id ?: null,
    site_name = site_name ?: null,
    site_name_long = site_name_long ?: null
)

fun LinksDTO.toDomain() : Links = Links(
    article_link = article_link ?: null, flickr_images = flickr_images, mission_patch = mission_patch ?: null, mission_patch_small = mission_patch_small ?: null, presskit = presskit ?: null, reddit_campaign = reddit_campaign ?: null, reddit_launch  = reddit_launch?: null, reddit_media  = reddit_media ?: null, reddit_recovery = reddit_recovery ?: null, video_link = video_link ?: null, wikipedia = wikipedia ?: null, youtube_id = youtube_id ?:  ""
)

fun RocketDTO.toDomain() : Rocket {

    val fairings = this.fairings?.toDomain()
    val first_stage = this.first_stage?.toDomain()
    val second_stage = this.second_stage?.toDomain()

    return Rocket(
        fairings = fairings ?: null,
        first_stage = first_stage ?: null,
        rocket_id = rocket_id ?: null,
        rocket_name = rocket_name ?: null,
        rocket_type = rocket_type ?: null,
        second_stage = second_stage
    )
}

fun FairingsDTO.toDomain() : Fairings = Fairings(
    recovered ?: null,
    recovery_attempt ?: null,
    reused ?: null,
    ship ?: null
)

fun FirstStageDTO.toDomain() : FirstStage {

    val cores = this.cores?.map {
        it.toDomain()
    }
    return FirstStage(
        cores
    )
}

fun CoreDTO.toDomain() : Core = Core(
    block ?: null, core_serial ?: null, flight ?: null, gridfins ?: null, land_success ?: null, landing_intent ?: null, landing_type ?: null, landing_vehicle ?: null, legs ?: null, reused = reused ?: true,
)
fun SecondStageDTO.toDomain() : SecondStage {

   /* val payload = payloads.map {
        it.toDomain()
    }*/

    return SecondStage(
        block = block
       // payloads
    )
}

/*fun PayloadDTO.toDomain() : Payload = Payload(

)*/

fun TelemetryDTO.toDomain() : Telemetry = Telemetry(
    flight_club = flight_club
)
fun TimelineDTO.toDomain() : Timeline = Timeline(
    beco = beco
)


