//
//  main.cpp
//  TestMacros
//
//  Created by Giorgi Shavgulidze on 24/02/16.
//  Copyright © 2016 Giorgi Shavgulidze. All rights reserved.
//

#include <iostream>

struct MatchPathSeparator { bool operator()( char ch ) const { return ch == '/'; } };

std::string basename(std::string const& pathname) {
    return std::string(
                       std::find_if( pathname.rbegin(), pathname.rend(),
                                    MatchPathSeparator() ).base(),
                       pathname.end() );
}

#define __File_Function_Line__ basename(__FILE__).c_str(), __func__, __LINE__
#define LevelPrinting(level, message) printf("[%s][%s][%s][%d] - %s\n", level, __File_Function_Line__, message);
#define PrintInfo(message)          LevelPrinting("INFO", message)
#define PrintWarning(message)       LevelPrinting("WARNING", message)
#define PrintError(message)          LevelPrinting("ERROR", message)
#define PrintDebug(message)          LevelPrinting("DEBUG", message)


void anotherFunction() {
    PrintDebug("Hello from the other side");
}

int main(int argc, const char * argv[]) {
    PrintInfo("Hello");
    anotherFunction();
    
    return 0;
}
