#import <Foundation/Foundation.h>

#import "VisionCameraCodeScanner.h"
#import "VisionCameraCodeScanner-Swift.h"

@implementation RegisterPlugins

    + (void) load {
        [self registerPlugin:[[VisionCameraCodeScanner alloc] init]];
    }

@end
