extern crate getopts;

use std::io::prelude::*;
use std::fs::File;
use std::io;
use std::result::Result;
use std::env;

#[derive(Copy, Clone)]
struct Grid {
    right: i32,
    down: i32,
    heading: Heading,
}

impl Grid {
    fn new(right: i32, down: i32, heading: Heading) -> Grid {
        Grid { right: right, down: down, heading: heading }
    }
}

#[derive(Copy, Clone)]
enum Direction {
    Left(i32),
    Right(i32)
}

#[derive(Copy, Clone)]
enum Heading {
    North,
    East,
    South,
    West,
}

fn main () {
    open().map(|contents| {
        let directions = contents.split(", ");
        let final_grid = directions.fold(Grid::new(0, 0, Heading::North), |grid, direction| {
            handle_direction(grid, direction.to_string())
        });
        println!("right: {}, down: {}", final_grid.right, final_grid.down);
        println!("total: {}", final_grid.right + final_grid.down);
    });
}

fn get_heading(direction: Direction, current_heading: Heading) -> Heading {
    match direction {
        Direction::Left(_) => get_heading_left(current_heading),
        Direction::Right(_) => get_heading_right(current_heading),
    }
}

fn get_heading_left(current_heading: Heading) -> Heading {
    match current_heading {
        Heading::North => Heading::West,
        Heading::East => Heading::North,
        Heading::South => Heading::East,
        Heading::West => Heading::South,
    }
}

fn get_heading_right(current_heading: Heading) -> Heading {
    match current_heading {
        Heading::North => Heading::East,
        Heading::East => Heading::South,
        Heading::South => Heading::West,
        Heading::West => Heading::North,
    }
}

fn get_new_grid(current_grid: Grid, amt: i32, new_heading: Heading) -> Grid {
    let result = match new_heading {
        Heading::North => Grid { down: current_grid.down - amt, right: current_grid.right, heading: new_heading },
        Heading::East => Grid { down: current_grid.down, right: current_grid.right + amt, heading: new_heading },
        Heading::South => Grid { down: current_grid.down + amt, right: current_grid.right, heading: new_heading },
        Heading::West => Grid { down: current_grid.down, right: current_grid.right - amt, heading: new_heading }
    };
    result
}

fn handle_direction(grid: Grid, direction: String) -> Grid {
    let gr = grid;
    let heading = get_heading(parse_direction(direction.clone()), gr.heading);
    let amt = parse_step_amount(direction);
    get_new_grid(grid, amt, heading)
}

fn parse_direction(unparsed: String) -> Direction {
    let amt = parse_step_amount(unparsed.clone());
    let char = unparsed.chars().nth(0).unwrap();
    match char {
        'L' => Direction::Left(amt),
        'R' => Direction::Right(amt),
        _ => panic!("Unparseable direction")
    }
}

fn parse_step_amount(unparsed: String) -> i32 {
    (&unparsed[1..].to_string()).parse::<i32>().unwrap()
}

fn open() -> Result<String, io::Error> {
    let mut input_path: String = String::new();
    input_path.push_str(env::current_dir().unwrap().to_str().unwrap());
    input_path.push_str("/day1/input.txt");
    let mut f: File = File::open(input_path)?;
    let mut buffer: String = String::new();
    f.read_to_string(&mut buffer)?;
    Ok(buffer)
}
