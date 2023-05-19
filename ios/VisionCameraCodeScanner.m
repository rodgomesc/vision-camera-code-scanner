#import <Foundation/Foundation.h>

#import "VisionCameraCodeScanner.h"
#if defined __has_include && __has_include("VisionCameraCodeScanner-Swift.h")
#import "VisionCameraCodeScanner-Swift.h"
#else
#import <VisionCameraCodeScanner/VisionCameraCodeScanner-Swift.h>
#endif

@implementation RegisterPlugins

    + (void) load {
        [self registerPlugin:[[VisionCameraCodeScanner alloc] init]];
    }

@end
